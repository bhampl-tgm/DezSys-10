package at.ac.tgm.hit.dezsys.hamplwortha.net;

import at.ac.tgm.hit.dezsys.hamplwortha.LoadBalancingAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles the LoadBalancer connections.
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public class LoadBalancerConnection implements Closeable, AutoCloseable {

    private static final Logger logger = LogManager.getLogger(LoadBalancerConnection.class.getName());
    private static final String SERVER_IDENTIFIER = "server";
    private final LoadBalancingAlgorithm loadBalancingAlgorithm;
    private ServerSocket serverSocket;
    private boolean run;

    /**
     * Creates a new load balancer connection.
     *
     * @param clientPort             the client port.
     * @param loadBalancingAlgorithm the load balancing algorithm.
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    public LoadBalancerConnection(int clientPort, LoadBalancingAlgorithm loadBalancingAlgorithm) throws IOException {
        this.serverSocket = new ServerSocket(clientPort);
        this.loadBalancingAlgorithm = loadBalancingAlgorithm;
        this.run = true;
    }

    /**
     * Starts the server.
     *
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    public void start() throws IOException {
        while (this.run) {
            Connection clientConnection = new Connection(this.serverSocket.accept());
            new Thread(() -> {
                try {
                    String clientMsg = new String(clientConnection.read());
                    logger.debug("LoadBalancer getting message with content: " + clientMsg);
                    if (this.isServer(clientMsg)) {
                        logger.info("Server connected, adding him to the server list");
                        Pattern pattern = Pattern.compile("server (\\d+) (\\d+)");
                        Matcher matcher = pattern.matcher(clientMsg);
                        if (matcher.matches()) {
                            clientConnection.setPort(Integer.parseInt(matcher.group(2)));
                            clientConnection.setHost(clientConnection.getSocket().getInetAddress().getHostAddress());
                            logger.debug("Connected Server: " + clientConnection.getSocket().getInetAddress().getHostAddress() + ":" + matcher.group(2));
                            this.loadBalancingAlgorithm.addServer(clientConnection, Integer.parseInt(matcher.group(1)));
                        } else {
                            logger.warn("The given server message was not correct: " + clientMsg);
                            clientConnection.close();
                        }
                        return;
                    }
                    if (this.loadBalancingAlgorithm.getServerCount() == 0) {
                        logger.warn("No server connected");
                        clientConnection.write("No server connected".getBytes());
                        clientConnection.close();
                        return;
                    }
                    Connection serverConnection = this.loadBalancingAlgorithm.getServer();
                    serverConnection.createNewSocket();
                    serverConnection.write(clientMsg.getBytes());
                    clientConnection.write(serverConnection.read());
                    serverConnection.close();
                    clientConnection.close();
                } catch (IOException e) {
                    logger.error("", e);
                    System.exit(1);
                }
            }).start();
        }
    }

    private boolean isServer(String msg) {
        return msg.startsWith(LoadBalancerConnection.SERVER_IDENTIFIER);
    }

    @Override
    public void close() throws IOException {
        logger.info("Shutting down LoadBalancerConnection");
        this.run = false;
        this.serverSocket.close();
    }
}

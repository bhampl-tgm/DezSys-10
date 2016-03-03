package at.ac.tgm.hit.dezsys.hamplwortha.net;

import at.ac.tgm.hit.dezsys.hamplwortha.Calculate;
import at.ac.tgm.hit.dezsys.hamplwortha.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * The server connection
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public class ServerConnection implements Closeable, AutoCloseable {

    private static final Logger logger = LogManager.getLogger(ServerConnection.class.getName());
    private final Calculate calculate;
    private final ServerSocket serverSocket;
    private final Server server;
    private boolean run;
    private int weight;

    public ServerConnection(int port, Calculate calculate, Server server, int weight) throws IOException {
        this.server = server;
        this.serverSocket = new ServerSocket(port);
        this.calculate = calculate;
        this.weight = weight;
        this.run = true;
    }

    public ServerConnection(int port, Calculate calculate, Server server) throws IOException {
        this(port, calculate, server, 1);
    }

    /**
     * Starts the server
     */
    public void start() throws IOException {
        while (this.run) {
            Connection clientConnection = new Connection(this.serverSocket.accept());
            new Thread(() -> {
                try {

                    String clientMsg = new String(clientConnection.read());
                    logger.debug("Server getting message with content: " + clientMsg);
                    if (clientMsg.equals("server connection count")) {
                        logger.debug("LoadBalancer Request request");
                        clientConnection.write((this.server.getCount() + "").getBytes());
                        clientConnection.close();
                        return;
                    }
                    logger.info("Incrementing server connection count to " + server.incrementCount());
                    clientConnection.write((calculate.calc(Long.parseLong(clientMsg.replaceAll("[\\D]", ""))) + "").getBytes());
                    clientConnection.close();
                    logger.info("Decrementing server connection count to " + server.decrementCount());
                } catch (Exception e) {
                    logger.error("", e);
                    System.exit(1);
                }
            }).start();
        }
    }

    @Override
    public void close() throws IOException {
        logger.info("Shutting down ServerConnection");
        this.run = false;
        this.serverSocket.close();
    }

    public void connectToLoadBalancer(String loadBalancerHost, int loadBalancerPort) throws IOException {
        Connection loadBalancerConnection = new Connection(loadBalancerHost, loadBalancerPort);
        loadBalancerConnection.write(("server " + this.weight + " " + this.serverSocket.getLocalPort()).getBytes());
        loadBalancerConnection.close();
    }
}

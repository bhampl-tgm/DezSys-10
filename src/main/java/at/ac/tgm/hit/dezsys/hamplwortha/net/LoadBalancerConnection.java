package at.ac.tgm.hit.dezsys.hamplwortha.net;

import at.ac.tgm.hit.dezsys.hamplwortha.LoadBalancer;
import at.ac.tgm.hit.dezsys.hamplwortha.LoadBalancingAlgorithm;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;

public class LoadBalancerConnection implements Closeable, AutoCloseable {

    private static final String SERVER_IDENTIFIER = "server";
    private final LoadBalancer loadBalancer;
    private ServerSocket serverSocket;
    private final LoadBalancingAlgorithm loadBalancingAlgorithm;
    private boolean run;

    public LoadBalancerConnection(int clientPort, LoadBalancer loadBalancer, LoadBalancingAlgorithm loadBalancingAlgorithm) throws IOException {
        this.serverSocket = new ServerSocket(clientPort);
        this.loadBalancer = loadBalancer;
        this.loadBalancingAlgorithm = loadBalancingAlgorithm;
        this.run = true;
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
                    if (this.isServer(clientMsg)) {
                        this.insertServer(clientMsg, clientConnection);
                        return;
                    }
                    Connection serverConnection = this.loadBalancingAlgorithm.getServer();
                    serverConnection.createNewSocket();
                    serverConnection.write(clientMsg.getBytes());
                    clientConnection.write(serverConnection.read());
                    serverConnection.close();
                    clientConnection.close();
                    this.loadBalancer.addServer(serverConnection);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }).start();
        }
    }

    private boolean isServer(String msg) {
        return msg.startsWith(LoadBalancerConnection.SERVER_IDENTIFIER);
    }

    private void insertServer(String msg, Connection connection) throws IOException {
        for (int i = 0; i < Integer.parseInt(msg.replaceAll("[\\D]", "")); i++) {
            // copy constructor because we want to close and open the sockets of the connection independently
            this.loadBalancer.addServer(new Connection(connection));
        }
        connection.getSocket().close();
    }

    @Override
    public void close() throws IOException{
        this.run = false;
        this.serverSocket.close();

    }
}

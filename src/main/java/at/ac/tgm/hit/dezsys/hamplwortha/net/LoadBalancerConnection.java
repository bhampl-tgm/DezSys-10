package at.ac.tgm.hit.dezsys.hamplwortha.net;

import at.ac.tgm.hit.dezsys.hamplwortha.LoadBalancingAlgorithm;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;

public class LoadBalancerConnection implements Closeable, AutoCloseable {

    private static final String SERVER_IDENTIFIER = "server";
    private final LoadBalancingAlgorithm loadBalancingAlgorithm;
    private ServerSocket serverSocket;
    private boolean run;

    public LoadBalancerConnection(int clientPort, LoadBalancingAlgorithm loadBalancingAlgorithm) throws IOException {
        this.serverSocket = new ServerSocket(clientPort);
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
                        this.loadBalancingAlgorithm.addServer(clientConnection, Integer.parseInt(clientMsg.replaceAll("[\\D]", "")));
                        return;
                    }
                    Connection serverConnection = this.loadBalancingAlgorithm.getServer();
                    serverConnection.createNewSocket();
                    serverConnection.write(clientMsg.getBytes());
                    clientConnection.write(serverConnection.read());
                    serverConnection.close();
                    clientConnection.close();
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

    @Override
    public void close() throws IOException {
        this.run = false;
        this.serverSocket.close();
    }
}

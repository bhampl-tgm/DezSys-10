package at.ac.tgm.hit.dezsys.hamplwortha.net;

import at.ac.tgm.hit.dezsys.hamplwortha.LoadBalancingAlgorithm;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;

public class LoadBalancerConnection implements Closeable, AutoCloseable {

    private ServerSocket serverSocket;
    private LoadBalancingAlgorithm loadBalancingAlgorithm;
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
                    Connection serverConnection = this.loadBalancingAlgorithm.getServer();
                    serverConnection.write(clientConnection.read());
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

    @Override
    public void close() throws IOException{
        this.run = false;
        this.serverSocket.close();

    }
}

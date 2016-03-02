package at.ac.tgm.hit.dezsys.hamplwortha.net;

import at.ac.tgm.hit.dezsys.hamplwortha.Calculate;
import at.ac.tgm.hit.dezsys.hamplwortha.Server;

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

    private final Calculate calculate;
    private final ServerSocket serverSocket;
    private final Server server;
    private boolean run;

    public ServerConnection(int port, Calculate calculate, Server server) throws IOException {
        this.server = server;
        this.serverSocket = new ServerSocket(port);
        this.calculate = calculate;
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
                    server.incrementCount();
                    String clientMsg = new String(clientConnection.read());
                    clientConnection.write((calculate.calc(Integer.parseInt(clientMsg.replaceAll("[\\D]", ""))) + "").getBytes());
                    clientConnection.close();
                    server.decrementCount();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }).start();
        }
    }

    @Override
    public void close() throws IOException {
        this.run = false;
        this.serverSocket.close();
    }
}

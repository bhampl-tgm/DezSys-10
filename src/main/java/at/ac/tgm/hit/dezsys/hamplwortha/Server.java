package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.ServerConnection;

import java.io.Closeable;
import java.io.IOException;

public class Server implements Closeable, AutoCloseable{

    private Calculate calculate;
    private final ServerConnection serverConnection;
    private boolean run;

    public Server(int serverPort, Calculate calculate) throws IOException {
        this.calculate = calculate;
        serverConnection = new ServerConnection(serverPort);
        this.run = false;
    }

    public void serve() throws IOException {
        this.serverConnection.start();
        while (this.run) {
            this.serverConnection.write(String.valueOf(this.calculate.calc(Integer.parseInt(new String(this.serverConnection.listenOnce())))).getBytes());
        }
    }

    @Override
    public void close() throws IOException {
        this.run = false;
        this.serverConnection.close();
    }
}

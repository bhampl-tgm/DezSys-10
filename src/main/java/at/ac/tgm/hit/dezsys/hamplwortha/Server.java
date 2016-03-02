package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.ServerConnection;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Closeable, AutoCloseable {

    private final AtomicInteger count;
    private final ServerConnection serverConnection;
    private Calculate calculate;
    private boolean run;

    public Server(int serverPort, Calculate calculate) throws IOException {
        this.calculate = calculate;
        this.serverConnection = new ServerConnection(serverPort, calculate, this);
        this.count = new AtomicInteger(0);
    }

    public void serve() throws IOException {
        this.serverConnection.start();
        // this.serverConnection.write(String.valueOf(this.calculate.calc(Integer.parseInt(new String(this.serverConnection.listenOnce())))).getBytes());
    }

    public void incrementCount() {
        this.count.incrementAndGet();
    }

    public void decrementCount() {
        this.count.decrementAndGet();
    }

    @Override
    public void close() throws IOException {
        this.serverConnection.close();
    }
}

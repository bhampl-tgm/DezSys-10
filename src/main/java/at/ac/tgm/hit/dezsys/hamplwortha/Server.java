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

    public Server(int serverPort, Calculate calculate, int weight) throws IOException {
        this.calculate = calculate;
        this.serverConnection = new ServerConnection(serverPort, calculate, this, weight);
        this.count = new AtomicInteger(0);
    }

    public void serve() throws IOException {
        this.serverConnection.start();
        // this.serverConnection.write(String.valueOf(this.calculate.calc(Integer.parseInt(new String(this.serverConnection.listenOnce())))).getBytes());
    }

    public void connectToLoadBalancer(String loadBalancerHost, int loadBalancerPort) throws IOException {
        this.serverConnection.connectToLoadBalancer(loadBalancerHost, loadBalancerPort);
    }

    public int incrementCount() {
        return this.count.incrementAndGet();
    }

    public int decrementCount() {
        return this.count.decrementAndGet();
    }

    public int getCount() {
        return this.count.get();
    }

    @Override
    public void close() throws IOException {
        this.serverConnection.close();
    }
}

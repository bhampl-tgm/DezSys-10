package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.ServerConnection;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class handles the server.
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public class Server implements Closeable, AutoCloseable {

    private final AtomicInteger count;
    private final ServerConnection serverConnection;
    private Calculate calculate;
    private boolean run;

    /**
     * Creates a new server.
     *
     * @param serverPort the server port.
     * @param calculate  the calculate.
     * @param weight     the weight.
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    public Server(int serverPort, Calculate calculate, int weight) throws IOException {
        this.calculate = calculate;
        this.serverConnection = new ServerConnection(serverPort, calculate, this, weight);
        this.count = new AtomicInteger(0);
    }

    /**
     * Starts the server.
     *
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    public void serve() throws IOException {
        this.serverConnection.start();
    }

    /**
     * Connects to a load balancer.
     *
     * @param loadBalancerHost the load balancer host
     * @param loadBalancerPort the load balancer port
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    public void connectToLoadBalancer(String loadBalancerHost, int loadBalancerPort) throws IOException {
        this.serverConnection.connectToLoadBalancer(loadBalancerHost, loadBalancerPort);
    }

    /**
     * Increments the counter.
     *
     * @return the amount after the action.
     */
    public int incrementCount() {
        return this.count.incrementAndGet();
    }

    /**
     * Decrements the counter.
     *
     * @return the amount after the action.
     */
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

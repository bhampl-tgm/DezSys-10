package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;
import at.ac.tgm.hit.dezsys.hamplwortha.net.LoadBalancerConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class handles the load balancer.
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public class LoadBalancer implements Closeable, AutoCloseable {
    private static final Logger logger = LogManager.getLogger(LoadBalancer.class.getName());
    private final LoadBalancerConnection loadBalancerConnection;
    private List<Connection> servers;

    /**
     * Creates a new load balancer.
     *
     * @param port               the port
     * @param balancingAlgorithm the load balancing algorithm
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    public LoadBalancer(int port, LoadBalancingAlgorithm balancingAlgorithm) throws IOException {
        this.servers = Collections.synchronizedList(new ArrayList<>());
        this.loadBalancerConnection = new LoadBalancerConnection(port, balancingAlgorithm);
    }

    /**
     * Starts the load balancer.
     *
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    public void balance() throws IOException {
        logger.info("Starting LoadBalancer");
        this.loadBalancerConnection.start();
    }

    /**
     * Adds a server connection to server connection list
     *
     * @param server the server connection.
     */
    public void addServer(Connection server) {
        this.servers.add(server);
    }

    /**
     * Removes a server connection from server connection list
     *
     * @param server the server connection.
     */
    public void removeServer(Connection server) {
        this.servers.remove(server);
    }

    public List<Connection> getServer() {
        return this.servers;
    }

    @Override
    public void close() throws IOException {
        logger.info("Shutting down LoadBalancer");
        this.loadBalancerConnection.close();
    }
}

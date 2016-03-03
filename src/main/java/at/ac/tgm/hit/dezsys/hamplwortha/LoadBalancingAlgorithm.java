package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;

import java.io.IOException;

/**
 * This class is a definition for load balancing algorithms.
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public interface LoadBalancingAlgorithm {
    /**
     * Returns a server evaluated with the load balancing algorithm.
     *
     * @return the server
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    Connection getServer() throws IOException;

    /**
     * Adds a server to the server list.
     *
     * @param connection the connection.
     * @param count      the count of servers.
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    void addServer(Connection connection, int count) throws IOException;

    /**
     * Removes a server from the server list.
     *
     * @param connection the connection.
     * @throws IOException if an I/O error occurs when creating the connection.
     */
    void removeServer(Connection connection) throws IOException;

    void setLoadBalancer(LoadBalancer loadBalancer);

    int getServerCount();
}

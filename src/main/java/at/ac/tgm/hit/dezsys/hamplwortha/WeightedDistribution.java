package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;

import java.io.IOException;

public class WeightedDistribution implements LoadBalancingAlgorithm {


    private LoadBalancer loadBalancer;

    /**
     * @see LoadBalancingAlgorithm#getServer()
     */
    @Override
    public Connection getServer() throws IOException {
        Connection connection = this.loadBalancer.getServer().remove(0);
        this.loadBalancer.addServer(new Connection(connection));
        return connection;
    }

    @Override
    public void addServer(Connection connection, int count) throws IOException, ClassNotFoundException {
        connection.getSocket().close();
        for (int i = 0; i < count; i++) {
            // copy constructor because we want to close and open the sockets of the connection independently
            this.loadBalancer.addServer(new Connection(connection));
        }
    }

    @Override
    public void addServer(Connection connection) throws IOException, ClassNotFoundException {
        this.addServer(connection, 1);
    }

    @Override
    public void removeServer(Connection connection) {
        this.loadBalancer.removeServer(connection);
    }


    @Override
    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public int getServerCount() {
        return this.loadBalancer.getServer().size();
    }
}

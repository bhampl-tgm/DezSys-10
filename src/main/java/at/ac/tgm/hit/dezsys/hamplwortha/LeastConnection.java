package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;

import java.io.IOException;

public class LeastConnection implements LoadBalancingAlgorithm {

    private LoadBalancer loadBalancer;

    /**
     * @see LoadBalancingAlgorithm#getServer()
     */
    public Connection getServer() throws IOException {
        Connection c = this.loadBalancer.getServer().get(0);
        int count = 2100000000;
        for (Connection connection: this.loadBalancer.getServer()){
            connection.createNewSocket();
            connection.write("server connection count".getBytes());
            int connectionCount = Integer.parseInt(new String(connection.read()));
            connection.close();
            if (connectionCount < count) {
                count = connectionCount;
                c = new Connection(connection);
            }
        }
        return c;
    }

    @Override
    public void addServer(Connection connection, int count) throws IOException {
        this.loadBalancer.addServer(new Connection(connection));
    }

    @Override
    public void addServer(Connection connection) throws IOException {
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

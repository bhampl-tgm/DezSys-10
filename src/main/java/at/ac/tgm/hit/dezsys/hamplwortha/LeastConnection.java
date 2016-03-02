package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;

import java.io.IOException;

public class LeastConnection implements LoadBalancingAlgorithm {
    /**
     * @see LoadBalancingAlgorithm#getServer()
     */
    public Connection getServer() {
        return null;
    }

    @Override
    public void addServer(Connection connection, int count) throws IOException {

    }

    @Override
    public void addServer(Connection connection) throws IOException {

    }

    @Override
    public void removeServer(Connection connection) {

    }

}

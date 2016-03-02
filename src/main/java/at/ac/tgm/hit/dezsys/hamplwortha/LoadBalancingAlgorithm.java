package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;

import java.io.IOException;

public interface LoadBalancingAlgorithm {
    Connection getServer() throws IOException;

    void addServer(Connection connection, int count) throws IOException;

    void addServer(Connection connection) throws IOException;

    void removeServer(Connection connection) throws IOException;
}

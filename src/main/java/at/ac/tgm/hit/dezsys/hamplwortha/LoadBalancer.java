package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;
import at.ac.tgm.hit.dezsys.hamplwortha.net.LoadBalancerConnection;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadBalancer implements Closeable, AutoCloseable {
    private final LoadBalancerConnection loadBalancerConnection;
    private List<Connection> servers;

    public LoadBalancer(int port, LoadBalancingAlgorithm balancingAlgorithm) throws IOException {
        this.servers = Collections.synchronizedList(new ArrayList<>());
        this.loadBalancerConnection = new LoadBalancerConnection(port, balancingAlgorithm);
    }

    public void balance() throws IOException {
        this.loadBalancerConnection.start();
    }

    public void addServer(Connection server) {
        this.servers.add(server);
    }

    public void removeServer(Connection server) {
        this.servers.remove(server);
    }

    public List<Connection> getServer() {
        return this.servers;
    }

    @Override
    public void close() throws IOException {
        this.loadBalancerConnection.close();
    }
}

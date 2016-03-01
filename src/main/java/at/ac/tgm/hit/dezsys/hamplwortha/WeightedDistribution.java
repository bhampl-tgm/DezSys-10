package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;

public class WeightedDistribution implements LoadBalancingAlgorithm {


    private LoadBalancer loadBalancer;

    public WeightedDistribution(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    /**
     * @see LoadBalancingAlgorithm#getServer()
     */
    public Connection getServer() {
        return this.loadBalancer.getServer().remove(0);
    }

}

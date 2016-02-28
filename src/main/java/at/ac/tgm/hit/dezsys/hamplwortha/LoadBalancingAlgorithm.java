package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;

public interface LoadBalancingAlgorithm {
    Connection getServer();
}

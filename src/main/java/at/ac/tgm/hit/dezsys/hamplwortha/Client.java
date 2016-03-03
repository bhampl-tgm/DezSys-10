package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

    private static final Logger logger = LogManager.getLogger(Client.class.getName());

    public void callCalculate(String host, int port, long iterations) {
        try {
            Connection clientconnection = new Connection(host, port);
            clientconnection.write((iterations + "").getBytes());
            logger.info("Calculate: " + new String(clientconnection.read()));
            clientconnection.close();
        } catch (Exception e) {
            logger.error("", e);
            System.exit(1);
        }
    }
}

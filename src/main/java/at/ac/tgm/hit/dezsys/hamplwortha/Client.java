package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Client {

    private static final Logger logger = LogManager.getLogger(Client.class.getName());

    public void callCalculate(String host, int port) {
        try {
            Connection clientconnection = new Connection(host, port);
            clientconnection.write("100000000".getBytes());
            logger.info("Calculate: " + new String(clientconnection.read()));
        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }
    }
}

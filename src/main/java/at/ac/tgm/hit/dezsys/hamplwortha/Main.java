package at.ac.tgm.hit.dezsys.hamplwortha;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            logger.info("Starting Application");
            new Parser().doMain(args);
        } catch (Exception e) {
            logger.error(e);
            System.exit(1);
        }
    }
}

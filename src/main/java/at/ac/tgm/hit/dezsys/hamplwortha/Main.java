package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.util.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This main class.
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    /**
     * The main method.
     *
     * @param args the command line args.
     */
    public static void main(String[] args) {
        try {
            logger.info("Starting Application");
            new Parser().doMain(args);
        } catch (Exception e) {
            logger.error("", e);
            System.exit(1);
        }
    }
}

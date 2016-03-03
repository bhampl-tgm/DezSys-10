package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.util.LogOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final Logger logger = LogManager.getLogger(Parser.class.getName());

    @Option(name = "-c", usage = "Starts Client")
    private boolean client;

    @Option(name = "-s", usage = "Starts Server")
    private boolean server;

    @Option(name = "-l", usage = "Starts Loddbalancer")
    private boolean lb;

    @Option(name = "-p", usage = "Port")
    private int port = 5555;

    @Option(name = "-h", usage = "Host")
    private String host = "127.0.0.1";

    @Option(name = "-i", usage = "Iterations of Pi-Calculation")
    private int iterations = 10;

    @Option(name = "-a", usage = "Algorithm, 1 ... Least Connection, 2 ... Weighted Distribution")
    private int alg;

    @Option(name = "-w", usage = "Weighted distribution")
    private int wd;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public void doMain(String[] args) throws IOException {
        ParserProperties properties = ParserProperties.defaults();
        properties.withUsageWidth(80);

        CmdLineParser parser = new CmdLineParser(this, properties);
        try {
            parser.parseArgument(args);

        } catch (CmdLineException e) {
            logger.error(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");

            parser.printUsage(new LogOutputStream(logger, logger.getLevel()));
            System.err.println();
        }
        //TODO Logic

        LoadBalancingAlgorithm lbAlg;

        if(server) {
            logger.info("Starting Server ...");
            new Server(port, new CalculatePi());
            logger.info("Server is running");
        }

        if(client) {
            logger.info("Starting Client ...");
            Client c = new Client();
            c.callCalculate(host, port);
            logger.info("Client is running");
        }

        if(alg == 1) {
            logger.info("Least Connections was set as Algorithm");
            lbAlg = new LeastConnection();
        }

        if(alg == 2) {
            logger.info("Weighted Distribution was set as Algorithm");
            //lbAlg = new WeightedDistribution(wd);
        }

        if(lb) {
            //TODO
            logger.info("Starting Loadbalancer ...");
            //new LoadBalancer(port, lbAlg);
            logger.info("Loadbalancer is running");
        }
    }
}

package at.ac.tgm.hit.dezsys.hamplwortha.util;

import at.ac.tgm.hit.dezsys.hamplwortha.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.*;
import org.kohsuke.args4j.spi.BooleanOptionHandler;
import org.kohsuke.args4j.spi.IntOptionHandler;
import org.kohsuke.args4j.spi.LongOptionHandler;
import org.kohsuke.args4j.spi.StringOptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final Logger logger = LogManager.getLogger(Parser.class.getName());

    @Option(name = "-c", usage = "Starts Client", handler = BooleanOptionHandler.class)
    private boolean client;

    @Option(name = "-s", usage = "Starts Server", handler = BooleanOptionHandler.class)
    private boolean server;

    @Option(name = "-l", usage = "Starts LoadBalancer", handler = BooleanOptionHandler.class)
    private boolean lb;

    @Option(name = "-u", usage = "Server: use LoadBalancer", handler = BooleanOptionHandler.class)
    private boolean ulb;

    @Option(name = "-P", usage = "LoadBalancer Port", handler = IntOptionHandler.class)
    private int lbPort = 5555;

    @Option(name = "-p", usage = "Port", handler = IntOptionHandler.class)
    private int port = 5555;

    @Option(name = "-h", usage = "Host (Client = Server/LB Host; Server: LB Host)", handler = StringOptionHandler.class)
    private String host = "127.0.0.1";

    @Option(name = "-i", usage = "Iterations of Pi-Calculation", handler = LongOptionHandler.class)
    private long iterations = 100000000;

    @Option(name = "-a", usage = "Algorithm, 1 ... Least Connection, 2 ... Weighted Distribution")
    private int alg;

    @Option(name = "-w", usage = "Weight of the server for the Weighted distribution", handler = IntOptionHandler.class)
    private int wd = 1;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public void doMain(String[] args) throws IOException {
        ParserProperties properties = ParserProperties.defaults();
        properties.withUsageWidth(80);

        CmdLineParser parser = new CmdLineParser(this, properties);
        try {
            parser.parseArgument(args);

            LoadBalancingAlgorithm lbAlg = new WeightedDistribution();

            if (args.length <= 0) {
                printUsage(parser);
                System.exit(0);
            }

            if (server) {
                logger.info("Starting Server ...");
                Server server = new Server(port, new CalculatePi(), wd);
                if (ulb) {
                    server.connectToLoadBalancer(host, lbPort);
                }
                server.serve();
                logger.info("Server is running");
            }

            if (client) {
                logger.info("Starting Client ...");
                Client c = new Client();
                c.callCalculate(host, port, iterations);
                logger.info("Client is running");
            }

            if (alg == 1) {
                logger.info("Least Connections was set as Algorithm");
                lbAlg = new LeastConnection();
            }

            if (alg == 2) {
                logger.info("Weighted Distribution was set as Algorithm");
                lbAlg = new WeightedDistribution();
            }

            if (lb) {
                logger.info("Starting Loadbalancer ...");
                LoadBalancer loadBalancer = new LoadBalancer(port, lbAlg);
                lbAlg.setLoadBalancer(loadBalancer);
                loadBalancer.balance();
                logger.info("Loadbalancer is running");
            }

        } catch (CmdLineException e) {
            logger.error(e.getMessage());
            printUsage(parser);
            System.exit(1);
        }
    }

    private void printUsage(CmdLineParser parser) {
        logger.error("java HamplWortha_Dezsys10-1.0 [options...] arguments...");
        parser.printUsage(new LogOutputStream(logger, Level.ERROR));
    }
}

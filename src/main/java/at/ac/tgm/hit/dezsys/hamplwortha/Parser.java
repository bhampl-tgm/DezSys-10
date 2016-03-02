package at.ac.tgm.hit.dezsys.hamplwortha;

import org.kohsuke.args4j.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {


    @Option(name = "-t", usage = "Typ: Server/Client/Loadbalancer gets started")
    private String type;

    @Option(name = "-p", usage = "Port")
    private int port;

    @Option(name = "-h", usage = "Host")
    private String host;

    @Option(name = "-i", usage = "Iterations of Pi-Calculation")
    private int iterations;

    @Option(name = "-a", usage = "Algorithm")
    private int alg;

    @Argument
    private List<String> arguments = new ArrayList<>();

    public void doMain(String[] args) throws IOException {
        //FIXME example code
        // if you have a wider console, you could increase the value;
        // here 80 is also the default
        ParserProperties properties = ParserProperties.defaults();
        properties.withUsageWidth(80);

        CmdLineParser parser = new CmdLineParser(this, properties);

        try {
            // parse the arguments.
            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");

            // after parsing arguments, you should check
            // if enough arguments are given.
            if (arguments.isEmpty())
                throw new IllegalArgumentException("No argument is given");

        } catch (CmdLineException e) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
        }
        //TODO Logic
    }
}

package at.ac.tgm.hit.dezsys.hamplwortha;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 26.02.2016.
 */
public class Parser {


    @Option(name="-t",usage="Typ: Server/Client/Loadbalancer gets started")
    private String type;

    @Option(name="-p",usage="Port")
    private int port;

    @Option(name="-h", usage="Host")
    private String host;

    @Option(name="-i", usage="Iterations of Pi-Calculation")
    private int iterations;

    @Option(name="-a", usage="Algorithm")
    private int alg;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        // if you have a wider console, you could increase the value;
        // here 80 is also the default
        parser.setUsageWidth(80);

        try {
            // parse the arguments.
            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");

            // after parsing arguments, you should check
            // if enough arguments are given.
            if( arguments.isEmpty() )
                throw new IllegalArgumentException("No argument is given");

        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            return;
        }
        //TODO Hier deklarieren was weiter passiert
    }
}

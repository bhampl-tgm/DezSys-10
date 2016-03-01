package at.ac.tgm.hit.dezsys.hamplwortha;

import at.ac.tgm.hit.dezsys.hamplwortha.net.Connection;

import java.io.IOException;

public class Client {
    public void callCalculate(String host, int port) {
        try {
            Connection clientconnection = new Connection(host, port);
            clientconnection.write("100000000".getBytes());
            System.out.println("Calculate: " + new String(clientconnection.read()));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

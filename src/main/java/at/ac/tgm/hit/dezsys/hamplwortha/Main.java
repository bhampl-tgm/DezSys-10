package at.ac.tgm.hit.dezsys.hamplwortha;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            new Parser().doMain(args);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

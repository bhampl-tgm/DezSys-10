package at.ac.tgm.hit.dezsys.hamplwortha.net;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server connection
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public class ServerConnection implements Closeable, AutoCloseable{

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;

    public ServerConnection(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Starts the server
     */
    public void start() {
        new Thread(() -> {
            try {
                this.clientSocket = this.serverSocket.accept();
                this.in = new DataInputStream(this.clientSocket.getInputStream());
                this.out = new DataOutputStream(this.clientSocket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }).start();
    }

    /**
     * Reads the first message form the server and returns it
     *
     * @return the message
     * @throws IOException if something went wrong
     */
    public byte[] listenOnce() throws IOException {
        int length = this.in.readInt();
        byte[] message = new byte[length];
        this.in.readFully(message, 0, message.length);
        return message;
    }

    /**
     * Sends a message to the client
     *
     * @param bytes the message in bytes
     * @throws IOException if something went wrong
     */
    public void write(byte[] bytes) throws IOException {
        this.out.writeInt(bytes.length);
        this.out.write(bytes);
    }

    @Override
    public void close() throws IOException{
        this.out.close();
        this.in.close();
        this.clientSocket.close();

    }

    public DataOutputStream getOut() {
        return this.out;
    }
}

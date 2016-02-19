package at.ac.tgm.hit.dezsys.hamplwortha.net;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * The client connection
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public class ClientConnection implements Closeable, AutoCloseable{

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String host = "";
    private int port = 0;

    /**
     * Creates a new connection
     *
     * @param host the host
     * @param port the port
     */
    public ClientConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Connects with the server
     *
     * @throws IOException if something went wrong
     */
    public void connect() throws IOException {
        this.socket = new Socket(this.host, this.port);
        this.in = new DataInputStream(this.socket.getInputStream());
        this.out = new DataOutputStream(this.socket.getOutputStream());
    }

    /**
     * Reads the message from the server
     *
     * @return the message
     * @throws IOException if something went wrong
     */
    public byte[] read() throws IOException {
        int length = this.in.readInt();
        if (length > 0) {
            byte[] message = new byte[length];
            this.in.readFully(message, 0, message.length);
            return message;
        }
        return null;
    }

    /**
     * Sends a message to the server
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
        this.socket.close();
    }
}

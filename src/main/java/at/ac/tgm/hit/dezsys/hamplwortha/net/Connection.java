package at.ac.tgm.hit.dezsys.hamplwortha.net;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection implements Closeable, AutoCloseable{

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(this.socket.getInputStream());
        this.out = new DataOutputStream(this.socket.getOutputStream());
    }

    public Socket getSocket() {
        return this.socket;
    }

    public DataInputStream getIn() {
        return this.in;
    }

    public DataOutputStream getOut() {
        return this.out;
    }

    public byte[] read() throws IOException {
        int length = this.in.readInt();
        if (length > 0) {
            byte[] message = new byte[length];
            this.in.readFully(message, 0, message.length);
            return message;
        }
        return null;
    }

    public void write(byte[] bytes) throws IOException {
        this.out.writeInt(bytes.length);
        this.out.write(bytes);
    }

    @Override
    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.socket.close();

    }
}

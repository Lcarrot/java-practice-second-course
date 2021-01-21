package chat.network;

import java.io.*;
import java.net.Socket;

public class ConnectionImpl implements Connection,Runnable {

    private final ConnectionListener listener;
    private OutputStream out;
    private InputStream in;
    private final Socket socket;
    private boolean isAlive;

    public ConnectionImpl(Socket socket, ConnectionListener listener) {
        this.socket = socket;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.listener = listener;
        Thread thread = new Thread(this);
        thread.start();
        isAlive = true;
    }

    @Override
    public void send(Message msg) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(msg);
            outputStream.flush();
        } catch (IOException e) {
            //ignore
        }
    }

    @Override
    public void close() {
        try {
            isAlive = false;
            socket.close();
        } catch (IOException e) {
            listener.connectException(this, e);
        }
    }

    @Override
    public boolean isActive() {
        return isAlive;
    }

    @Override
    public void run() {
        while (isAlive) {
            try {
                if (in.available() != 0) {
                    ObjectInputStream inputStream = new ObjectInputStream(in);
                    listener.receiveContent((Message) inputStream.readObject());
                } else {
                    Thread.sleep(500);
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                listener.connectException(this, e);
            }
        }
    }

    @Override
    public String toString() {
        return "ConnectionImpl{" +
                "socket=" + socket +
                '}';
    }
}

package chat.server;

import chat.network.Connection;
import chat.network.ConnectionImpl;
import chat.network.ConnectionListener;
import chat.network.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server implements ConnectionListener {

    private final List<Connection> connectionSet;
    private final ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(Connection.PORT);
            connectionSet = new LinkedList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        System.out.println("Server was started");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                openConnection(new ConnectionImpl(socket, this));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public synchronized void openConnection(Connection connection) {
        connectionSet.add(connection);
        System.out.println("Connection was added: " + connection.toString());
    }

    @Override
    public synchronized void closeConnection(Connection connection) {
        connectionSet.remove(connection);
        System.out.println("Connection was removed: " + connection.toString());
        connection.close();
    }

    @Override
    public synchronized void connectException(Connection connection, Exception exception) {
        exception.printStackTrace();
        if (connectionSet.contains(connection)) closeConnection(connection);
    }

    public synchronized void receiveContent(Message msg) {
        for (Connection connection: connectionSet) {
            if (!connection.isActive()) {
                closeConnection(connection);
            }
            else {
                connection.send(msg);
            }
        }
    }
}

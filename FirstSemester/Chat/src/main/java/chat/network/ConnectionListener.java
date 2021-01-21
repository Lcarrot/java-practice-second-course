package chat.network;

public interface ConnectionListener {

    void openConnection(Connection connection);
    void closeConnection(Connection connection);
    void connectException(Connection connection, Exception exception);
    void receiveContent(Message msg);
}

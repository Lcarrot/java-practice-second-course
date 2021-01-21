package chat.network;

public interface Connection {

    int PORT = 666;

    void send(Message msg);
    void close();
    boolean isActive();
}

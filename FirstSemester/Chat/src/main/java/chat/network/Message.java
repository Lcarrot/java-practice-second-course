package chat.network;

import java.io.Serializable;

public interface Message<T> extends Serializable {

    int CLOSE_TYPE = 0;
    int CONTENT_TYPE = 1;

    String getNick();

    T getContent();

    int getType();
}

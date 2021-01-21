package chat.network;

public class StringMessage implements Message<String> {

    private final String nick;
    private final String content;
    private final int type;

    public StringMessage(String nick, String content, int type) {
        this.nick = nick;
        this.content = content;
        this.type = type;
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return nick + ": " + content;
    }
}

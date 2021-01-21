package chat.client;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class ClientUI extends JFrame {
    private JTextField userName;
    private JTextField message;
    private JTextArea chat;
    private JButton send;
    private JPanel rootPanel;

    public ClientUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(rootPanel);
        this.chat.setEditable(false);
        pack();
    }

    public JTextField getUserName() {
        return userName;
    }

    public JTextField getMessage() {
        return message;
    }

    public JTextArea getChat() {
        return chat;
    }

    public JButton getSend() {
        return send;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void setActionOnSend(ActionListener listener) {
        send.addActionListener(listener);
    }

    public void setWindowListener(WindowListener windowListener) {
        addWindowListener(windowListener);
    }
}

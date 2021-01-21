package chat.client;

import chat.network.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientListener implements ConnectionListener, ActionListener, WindowListener {


    public static void main(String[] args) {
        ClientUI clientUI = new ClientUI("positive chat");
        ClientListener listener = new ClientListener(clientUI);
        clientUI.setActionOnSend(listener);
        clientUI.setWindowListener(listener);
        clientUI.setVisible(true);
    }

    private ClientUI clientUI;
    private Connection connection;

    public ClientListener(ClientUI clientUI) {
        try {
            this.clientUI = clientUI;
            Socket socket = new Socket(InetAddress.getLocalHost(), Connection.PORT);
            connection = new ConnectionImpl(socket, this);
            openConnection(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void printMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            JTextArea chat = clientUI.getChat();
            chat.append(message + "\n");
            chat.setCaretPosition(chat.getDocument().getLength());
        });
    }

    @Override
    public void openConnection(Connection connection) {
        printMessage("Connection is ready");
    }

    @Override
    public void closeConnection(Connection connection) {
        printMessage("connection closed");
        System.out.println("close connection");
        connection.close();
    }

    @Override
    public void connectException(Connection connection, Exception exception) {
        printMessage("error: " + exception.getMessage());
        System.out.println(exception.getMessage());
        if (connection.isActive()) {
            closeConnection(connection);
        }
    }

    @Override
    public void receiveContent(Message msg) {
        printMessage(msg.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField message = clientUI.getMessage();
        String msg = message.getText();
        if (msg.equals("")) return;
        message.setText(null);
        connection.send(new StringMessage(clientUI.getUserName().getText(), msg, 1));
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (connection != null && connection.isActive()) {
            closeConnection(connection);
        }
        e.getWindow().dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

package client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple client with a single window frame. Sends a username as a String to
 * server and receives email address String as reply.
 * <p>
 * The reply is read in the main thread while the sending is done in the action
 * listener thread.
 *
 * @author Chris Bass
 * 08/04/2016
 */
public class Client extends Frame {

    private int clientNumber = 0;
    private final Object waitObject = new Object();
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private Socket socket;


    /**
     * Constructor will just initialise the AWT Frame and GUI.
     */
    public Client() {
        super();
    }

    /**
     * Close the connection to server.
     */
    public void closeConnection(JLabel displayLabel) {
        if (socket != null) {
            clientSays("Closing connection" , displayLabel);
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                socket = null;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    /**
     * Send username to server.
     */
    public void sendToServer(JTextField textFieldUsername , JLabel displayLabel) {
        if (printWriter != null && bufferedReader != null) {

            // 1. read data from textfield
            String toSend = textFieldUsername.getText();

            // 2. send data to server
            clientSays("Sending " + toSend + " to server." , displayLabel);
            printWriter.println(toSend);

        } else {
            clientSays("You must connect to the server first!!" , displayLabel);
        }
    }

    /**
     * Setup connection to the server on the loop back address and the same port
     * number as the Server is expecting.
     */
    public void reconnectToServer(JLabel displayLabel) {
        closeConnection(displayLabel);
        clientSays("Attempting connection to server" , displayLabel);
        try {
            socket = new Socket("127.0.0.1", 2000);

            printWriter = new PrintWriter(socket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientSays("Connected to server" , displayLabel);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            clientSays(ex.toString() , displayLabel); // connection failed
        }

        // notify that the connection is back
        synchronized (waitObject) {
            waitObject.notify();
        }
    }

    /**
     * Keep reading for messages from the server and updating the GUI.
     */
    public void keepReadingFromServer(JTextField textFieldEmail, JLabel displayLabel) {
        int count = 0;
        while (true) {
            count++;

            // if we have lost connection then just pause this loop until we
            // receive notification to start running again.
            if (socket == null) {
                clientSays("Waiting for connection to be reset..." , displayLabel);
                synchronized (waitObject) {
                    try {
                        waitObject.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            clientSays("Waiting for message" + count + " from server..." , displayLabel);
            String reply = null;
            try {
                reply = bufferedReader.readLine();
                clientSays("Received \"" + reply + "\" from server." , displayLabel);
            } catch (IOException ex) {
                clientSays("IOException " + ex , displayLabel);
            }

            if (reply != null && reply.startsWith("You are client number")) {
                clientNumber = Integer.parseInt(reply.substring(21));
            } else {
                textFieldEmail.setText(reply);
            }
        }
    }

    /**
     * Private helper method outputs to standard output stream for debugging.
     *
     * @param say the String to write to standard output stream.
     */
    public void clientSays(String say , JLabel displayLabel) {
        System.out.println("Client" + clientNumber + ": " + say);
        displayLabel.setText("Status: " + say);
    }

    public static void main(String[] args) {
        Client simpleClient = new Client();
        ClientGUI gui = new ClientGUI();
        JLabel displayLabel = gui.labelStatus;
        JTextField textFieldEmail = gui.textFieldEmail;
        simpleClient.reconnectToServer(displayLabel);
        simpleClient.keepReadingFromServer(textFieldEmail,displayLabel);
    }

}
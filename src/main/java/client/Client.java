package client;

import common.OnLoan;
import common.Person;
import common.Books;
import common.SQLite;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.List;
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
    private Button buttonConnect;
    private Label labelStatus;
    private TextField textFieldUsername;
    private TextField textFieldEmail;
    private Button buttonSend;
    private Button sqlExecute;
    private Label timeLabel;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private Socket socket;
    private TextField sqlcommandIN;

    /**
     * Constructor will just initialise the AWT Frame and GUI.
     */
    public Client() {
        super();
        initGUI();
    }

    /**
     * Initialise Simple Client GUI using AWT library
     */
    private void initGUI() {
        this.setTitle("Simple Client");
        buttonConnect = new Button("Connect to Server");
        labelStatus = new Label("Status: Initialising GUI");
        textFieldUsername = new TextField(20);
        textFieldEmail = new TextField(20);
        buttonSend = new Button("Send to Server");
        timeLabel = new Label("Time here");
        sqlExecute = new Button("SQLite Command Run");
        sqlcommandIN = new TextField(20);
        this.setLayout(new GridLayout(5, 2));
        this.add(buttonConnect);
        this.add(labelStatus);
        this.add(new Label("Username to send to server:"));
        this.add(textFieldUsername);
        this.add(new Label("Email address reply from server:"));
        this.add(textFieldEmail);
        this.add(buttonSend);
        this.add(timeLabel);
        this.add(sqlExecute);
        this.add(sqlcommandIN);
        this.setSize(700, 300);
        this.setVisible(true);
        this.setAlwaysOnTop(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
                closeConnection();
                System.exit(0);
            }
        });

        buttonConnect.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconnectToServer();
            }

        });

        buttonSend.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendToServer();
            }

        });
        sqlExecute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sqliteCommand = sqlcommandIN.getText();
                SQLite app = new SQLite(sqliteCommand);
                List<OnLoan> trackList = app.executeSQLCommandP();
                for (OnLoan t : trackList) {
                    System.out.println(t);
                }
            }
        });
    }

    /**
     * Close the connection to server.
     */
    private void closeConnection() {
        if (socket != null) {
            clientSays("Closing connection");
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
    private void sendToServer() {
        if (printWriter != null && bufferedReader != null) {

            // 1. read data from textfield
            String toSend = textFieldUsername.getText();

            // 2. send data to server
            clientSays("Sending " + toSend + "to server.");
            printWriter.println(toSend);

        } else {
            clientSays("You must connect to the server first!!");
        }
    }

    /**
     * Setup connection to the server on the loop back address and the same port
     * number as the Server is expecting.
     */
    private void reconnectToServer() {
        closeConnection();
        clientSays("Attempting connection to server");
        try {
            socket = new Socket("127.0.0.1", 2000);

            printWriter = new PrintWriter(socket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientSays("Connected to server");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            clientSays(ex.toString()); // connection failed
        }

        // notify that the connection is back
        synchronized (waitObject) {
            waitObject.notify();
        }
    }

    /**
     * Keep reading for messages from the server and updating the GUI.
     */
    private void keepReadingFromServer() {
        int count = 0;
        while (true) {
            count++;

            // if we have lost connection then just pause this loop until we
            // receive notification to start running again.
            if (socket == null) {
                clientSays("Waiting for connection to be reset...");
                synchronized (waitObject) {
                    try {
                        waitObject.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            clientSays("Waiting for message" + count + " from server...");
            String reply = null;
            try {
                reply = bufferedReader.readLine();
                clientSays("Received \"" + reply + "\" from server.");
            } catch (IOException ex) {
                clientSays("IOException " + ex);
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
    private void clientSays(String say) {
        System.out.println("Client" + clientNumber + ": " + say);
        labelStatus.setText("Status: " + say);
    }

    public static void main(String[] args) {
        Client simpleClient = new Client();
        simpleClient.reconnectToServer();
        simpleClient.keepReadingFromServer();
    }

}
package client;

import common.Message;

import java.util.ArrayList;
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
public class Client {

    private final Object waitObject = new Object();
    private Socket socket;
    public Message toSend;
    public ArrayList<?>toPlot;

    /**
     * Constructor will just initialise the AWT Frame and GUI.
     */
    public Client() {

        super();
    }

    /**
     * Close the connection to server.
     */
    public String closeConnection() {
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
        return "Connection Closed";
    }
    /**
     * Setup connection to the server on the loop back address and the same port
     * number as the Server is expecting.
     */
    public String reconnectToServer() {
        closeConnection();
        clientSays("Attempting connection to server");
        try {
            socket = new Socket("127.0.0.1", 2000);
            clientSays("Connected to server");
            return "Connected To Server";
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            clientSays(ex.toString()); // connection failed
        }

        // notify that the connection is back
        synchronized (waitObject) {
            waitObject.notify();
        }
        return "Connection to Server Failed";
    }

    /**
     * Keep reading for messages from the server and updating the GUI.
     */


    public String readFromServer() {

            clientSays("Waiting for message from server...");
            try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())){
                ArrayList<?> replyObject;
                replyObject = (ArrayList<?>) objectInputStream.readObject();
                toPlot = replyObject;
            } catch (IOException | ClassNotFoundException ex) {
                clientSays("IOException " + ex);
            }
            return "Read From Server Complete";

        }
    //End of method
    public String sendToServer(Message toSend) {
        this.toSend = toSend;
        try {
            if (this.socket!=null) {
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                // send data to server
                clientSays("Sending " + toSend + " to server.");
                objectOutputStream.writeObject(toSend);
            }
        } catch (IOException ex) {
            clientSays("IOException : " + ex);
        }
        return "Sent to server :"+ toSend;
    }

    /**
     * Private helper method outputs to standard output stream for debugging.
     *
     * @param say the String to write to standard output stream.
     */
    public void clientSays(String say) {
        int clientNumber = 0;
        System.out.println("Client" + clientNumber + ": " + say);
    }


    public static void main(String[] args) {

        Client client = new Client();
        ClientGUI gui = new ClientGUI();
    }

}
package client;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple client with a single window frame (This is delegated to another class the ClientGUI).
 * Sends a Message Object to server and receives Arraylist of database objects as reply.
 * <p>
 * The reply is read in the main thread while the sending is done in the action
 * listener thread.
 *
 * @author Umair Afzal
 *
 */
public class Client {

    private final Object waitObject = new Object();
    public Message toSend;
    public ArrayList<?> toPlot;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
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
     * @return String, This string signifies if the connection was successful or failed
     */
    public String reconnectToServer() {
        closeConnection();
        clientSays("Attempting connection to server");
        try {
            socket = new Socket("127.0.0.1", 2000);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
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
     * Read for messages(In our case Arraylists) from the server and sets it as a class variable.
     */


    public void readFromServer() {
        if(objectInputStream!=null){
            clientSays("Waiting for message from server...");
            try {
                ArrayList<?> replyObject;
                replyObject = (ArrayList<?>) objectInputStream.readObject();
                toPlot = replyObject;
            } catch (IOException | ClassNotFoundException ex) {
                clientSays("IOException " + ex);
            }
        }else{
            clientSays("Please Connect to Server");
        }

    }

    /**
     * Sends a message object to the server via an ObjectOutputStream
     * @param toSend Takes a Message Object as a parameter
     * @return  Returns a String which confirms what exactly was sent to the server
     */
    public String sendToServer(Message toSend) {
        this.toSend = toSend;
        try {

            if (this.socket != null) {
                clientSays("Sending " + toSend + " to server.");
                objectOutputStream.writeObject(toSend);
                objectOutputStream.reset();
            }
        } catch (IOException ex) {
            clientSays("IOException : " + ex);
        }
        return "Sent to server :" + toSend;
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

       new Client();
       new ClientGUI();
    }


}
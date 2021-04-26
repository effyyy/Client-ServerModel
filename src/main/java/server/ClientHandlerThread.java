package server;

import client.Client;
import client.ClientGUI;
import common.Command;
import common.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is our thread class with the responsibility of handling client requests
 * once the client has connected. A socket is stored to allow connection.
 * <p>
 * There are two ways to make a thread, one is to extend from the Thread class.
 * The other way is to implement the Runnable interface. Implementing Runnable
 * is better because we do not have to waste our inheritance option.
 *
 * @author Chris Bass
 * 08/04/2016
 */
public class ClientHandlerThread implements Runnable{

    private final Socket socket;
    private static int connectionCount = 0;
    private final int connectionNumber;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;


    ThreadedServer threadedServer = new ThreadedServer();

    /**
     * Constructor just initialises the connection to client.
     *
     * @param socket       the socket to establish the connection to client.
     * @throws IOException if an I/O error occurs when creating the input and
     *                     output streams, or if the socket is closed, or socket is not connected.
     */
    public ClientHandlerThread(Socket socket) throws IOException {
        this.socket = socket;
        connectionCount++;
        connectionNumber = connectionCount;
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        threadSays("Connection " + connectionNumber + " established.");
    }

    /**
     * The run method is overridden from the Runnable interface. It is called
     * when the Thread is in a 'running' state - usually after thread.start()
     * is called. This method reads client requests and processes names until
     * an exception is thrown.
     */
    @Override
    public void run() {
        try
        {
            Message messageRead;

            ArrayList<?> objectList;

            while ((messageRead = (Message)objectInputStream.readObject())!=null) {
                System.out.println("Object Read is :" + messageRead);
                objectList = threadedServer.getData(messageRead);
                System.out.println(objectList);
                objectOutputStream.writeObject(objectList);
            }

        }catch(IOException | ClassNotFoundException ex){
            threadSays("Exception : " + ex);

        }finally {

            try {
                threadSays("We have lost connection to client " + connectionNumber + ".");
                ThreadedServer.removeThread(this);
                socket.close();

            } catch (IOException ex) {
                Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

 public void sendBroadcast() {
        threadSays("Broadcasting to client " + connectionNumber + ".");
    }

    /**
     * Private helper method outputs to standard output stream for debugging.
     *
     * @param say the String to write to standard output stream.
     */
    private void threadSays(String say) {
        System.out.println("ClientHandlerThread" + connectionNumber + ": " + say);
    }

}
package server;

import common.Command;
import common.Database;
import common.Message;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
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

    ThreadedServer test = new ThreadedServer();

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
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            Message messageRead;
            List<?> testObject = null;
            while ((messageRead = (Message) objectInputStream.readObject()) != null) {
                System.out.println("Object Read is :" + messageRead);
                if (messageRead.getCommand() == Command.UPDATE) {
                    ThreadedServer.broadcastToClients();
                }
                if (messageRead.getCommand() == Command.SELECT) {
                    testObject = test.selectTable(messageRead);
                    objectOutputStream.writeObject(testObject);
                }
            }
        }catch(IOException | ClassNotFoundException ex){
            threadSays("Exception : " + ex);
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
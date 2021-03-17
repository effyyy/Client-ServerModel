package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
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
public class ClientHandlerThread implements Runnable {

    private final Socket socket;
    private final HashMap<String, String> hashMapNames;

    private final PrintWriter printWriter;
    private final BufferedReader bufferedReader;

    private static int connectionCount = 0;
    private final int connectionNumber;

    /**
     * Constructor just initialises the connection to client.
     *
     * @param socket       the socket to establish the connection to client.
     * @param hashMapNames a reference to the lookup table for getting email.
     * @throws IOException if an I/O error occurs when creating the input and
     *                     output streams, or if the socket is closed, or socket is not connected.
     */
    public ClientHandlerThread(Socket socket, HashMap hashMapNames) throws IOException {
        this.socket = socket;
        this.hashMapNames = hashMapNames;

        printWriter = new PrintWriter(socket.getOutputStream(), true);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        connectionCount++;
        connectionNumber = connectionCount;
        printWriter.println("You are client number" + connectionNumber);
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
        try {
            // Read and process names until an exception is thrown.
            threadSays("Waiting for data from client...");
            String lineRead;
            while ((lineRead = bufferedReader.readLine()) != null) {
                threadSays("Read data from client: \"" + lineRead + "\".");

                if (lineRead.startsWith("BROADCAST")) {
                    ThreadedServer.broadcastToClients();
                } else {
                    String emailLookup = hashMapNames.getOrDefault(lineRead, "User not known");
                    printWriter.println(emailLookup);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
        printWriter.println("SERVERBROADCAST: " + new Date().toString());
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
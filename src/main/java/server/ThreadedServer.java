package server;

import common.*;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A threaded server can handle multiple client's requests at the same time via
 * multi-threading. This class has the responsibility for connecting new clients
 * and starting a new thread for each new client.
 *
 * @author Chris Bass
 * 08/04/2016
 */
public class ThreadedServer{

    private static final HashSet<ClientHandlerThread> CLIENT_HANDLER_THREADS = new HashSet<>();
    SQLite sql = new SQLite();

    /**
     * Constructor will just initialise the HashMap lookup table on the Server.
     */
    public ThreadedServer() {
    }
    /**
     * Wait until a client connects to the server on a port, then establish the
     * connection via a socket object and create a thread to handle requests.
     */
    private void connectToClients() {
        System.out.println("Server: Server starting.");

        try (ServerSocket serverSocket = new ServerSocket(2000)) {
            int connectionCount = 0;

            while (true) {
                System.out.println("Server: Waiting for connecting client...");
                try (Socket socket = serverSocket.accept()) {
                    connectionCount++;
                    System.out.println("Server: Connection " + connectionCount + " established.");
                    ClientHandlerThread clientHandlerThread = new ClientHandlerThread(socket);
                    clientHandlerThread.run();

                } catch (IOException ex) {
                    System.out.println("Server: We have lost connection to client " + connectionCount + ".");
                }
            }
        }catch(IOException ex){
            System.out.println("IO Exception : " + ex);
        }
    }

    public static void removeThread(ClientHandlerThread threadToRemove) {
        CLIENT_HANDLER_THREADS.remove(threadToRemove);
    }

    public static void broadcastToClients() {

        for (ClientHandlerThread handler : CLIENT_HANDLER_THREADS) {
            handler.sendBroadcast();
        }
    }

    public List<? extends Object> selectTable(Message message) {
        if (message.getCommand() == null) {
            System.out.println("Null input");
            return null;
        } else {
            if (message.getDatabase() == Database.BOOKS) {
                List<Books> books = sql.executeSQLCommandBooks();
                return books;
            }
            if (message.getDatabase() == Database.PERSONS) {
                List<Person> person = sql.executeSQLCommandPerson();
                return person;
            }
            if (message.getDatabase()==Database.ON_LOAN) {
                List<OnLoan> onloan = sql.executeSQLCommandOnLoan();
                return onloan;
            }
            return null;
        }

    }

    public static void main(String[] args) {
        ThreadedServer simpleServer = new ThreadedServer();
        simpleServer.connectToClients();
    }

}
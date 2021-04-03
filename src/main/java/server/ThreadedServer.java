package server;

import common.Books;
import common.Person;
import common.OnLoan;

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
public class ThreadedServer {

    private HashMap<String, String> hashMapNames;
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

            while (true) {
                System.out.println("Server: Waiting for connecting client...");

                try {
                    Socket socket = serverSocket.accept();

                    ClientHandlerThread clientHandlerThread = new ClientHandlerThread(socket, hashMapNames);
                    Thread connectionThread = new Thread(clientHandlerThread);
                    connectionThread.start();
                    CLIENT_HANDLER_THREADS.add(clientHandlerThread);
                } catch (IOException ex) {
                    System.out.println("Server: Could not start connection to a client.");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server: Closed down");
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

    public List<? extends Object> selectTable(String tableSelect) {
        if (tableSelect == null) {
            System.out.println("Null input");
            return null;
        } else {
            System.out.println("Received String is.... " + tableSelect);
            if (tableSelect.equals("SQLExecute : 1")) {
                List<Books> books = sql.executeSQLCommandBooks();
                return books;
            }
            if (tableSelect.equals("SQLExecute : 2")) {
                List<Person> person = sql.executeSQLCommandPerson();
                return person;
            }
            if (tableSelect.equals("SQLExecute : 3")) {
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
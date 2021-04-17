package server;

import common.*;

import java.io.*;
import java.util.*;
import java.net.*;


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
                try {
                    Socket socket = serverSocket.accept();
                    connectionCount++;
                    System.out.println("Server: Connection " + connectionCount + " established.");
                    ClientHandlerThread clientHandlerThread = new ClientHandlerThread(socket);
                    Thread connectionThread = new Thread(clientHandlerThread);
                    connectionThread.start();
                    CLIENT_HANDLER_THREADS.add(clientHandlerThread);
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

    public ArrayList<?> getData(Message message) {

        String sqlStatement;

        if (message.getCommand() == null) {
            System.out.println("Null input");
        } else {
                sqlStatement = getSQLStatement(message);
            System.out.println(sqlStatement);

                if (message.getDatabase() == Database.BOOKS) {
                    return sql.executeSQLCommandBooks(sqlStatement);
                }
                if (message.getDatabase() == Database.PERSON) {
                    return sql.executeSQLCommandPerson(sqlStatement);
                }
                if (message.getDatabase() == Database.ON_LOAN) {
                    return sql.executeSQLCommandOnLoan(sqlStatement);
                }
            }
        return null;

    }

    public String getSQLStatement(Message message){
        if(message.getCommand() == Command.SELECT_ALL){
            return "SELECT * FROM "+ message.getDatabase();
        }
        if(message.getCommand()==Command.SELECT_WHERE){
            return "SELECT * FROM "+ message.getDatabase() + " WHERE "+ message.getArgument();
        }
        else{
            return null;
        }
    }

    public static void main(String[] args) {
        ThreadedServer simpleServer = new ThreadedServer();
        simpleServer.connectToClients();
    }

}
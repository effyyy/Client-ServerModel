//Name :- Umair Afzal
//SID :- 8975414

package server;

import common.Command;
import common.Database;
import common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * A threaded server can handle multiple client's requests at the same time via
 * multi-threading. This class has the responsibility for connecting new clients
 * and starting a new thread for each new client.
 *
 */
public class ThreadedServer {

    private static final HashSet<ClientHandlerThread> CLIENT_HANDLER_THREADS = new HashSet<>();
    SQLite sql = new SQLite();

    /**
     * Constructor will just initialise the HashMap lookup table on the Server.
     */
    public ThreadedServer() {
    }

    public static void removeThread(ClientHandlerThread threadToRemove) {
        CLIENT_HANDLER_THREADS.remove(threadToRemove);
    }

    public static void main(String[] args) {
        ThreadedServer simpleServer = new ThreadedServer();
        simpleServer.connectToClients();
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
        } catch (IOException ex) {
            System.out.println("IO Exception : " + ex);
        }
    }

    /**
     * This class takes a message object and passes it into the sqlStatementCreator, this returns a SQL executable
     * Statement which is then executed using the SQLite class, this function then returns any resulting database objects
     * as an arraylist to be sent to the server.
     * @param message Takes a message object as an input
     * @return
     */
    public ArrayList<?> getData(Message message) {

        String sqlStatement;

        if (message.getCommand() == null) {
            System.out.println("Null input");
        } else {
            sqlStatement = sqlStatementCreator(message);
            System.out.println("Created SQL Statement is : "+sqlStatement);

            if (message.getDatabase() == Database.BOOKS) {
                return sql.executeSQLCommandBooks(sqlStatement, message);
            }
            if (message.getDatabase() == Database.PERSON) {
                return sql.executeSQLCommandPerson(sqlStatement, message);
            }
            if (message.getDatabase() == Database.ON_LOAN) {
                return sql.executeSQLCommandOnLoan(sqlStatement, message);
            }
        }
        return null;

    }

    /**
     * Takes a Message object and creates a SQL statement out of it using the command,database and argument present in
     * the Message object.
     * @param message Message object as input
     * @return Returns an executable SQL statement
     */
    public String sqlStatementCreator(Message message) {

        if (message.getCommand() == Command.SELECT_ALL) {
            return "SELECT * FROM " + message.getDatabase();
        }


        if (message.getCommand() == Command.SELECT_WHERE) {
            return "SELECT * FROM " + message.getDatabase() + " WHERE " + message.getArgument();
        }


        if (message.getCommand() == Command.INSERT_INTO) {
            if (message.getDatabase() == Database.PERSON) {
                return "INSERT INTO " + message.getDatabase() + "(first_name,last_name,library_card) VALUES (?,?,?)";
            }
            if (message.getDatabase() == Database.BOOKS) {
                return "INSERT INTO " + message.getDatabase() + "(title,authors,average_rating,isbn,isbn13,language_code," +
                        "[#num_pages],ratings_count,text_reviews_count,quantity) VALUES (?,?,?,?,?,?,?,?,?,?)";
            }
            if (message.getDatabase() == Database.ON_LOAN) {
                return "INSERT INTO " + message.getDatabase() + "(person_id,book_id,loan_period,loan_start,loan_end," +
                        "returned_date,return_status) VALUES (?,?,?,?,?,?,?)";
            }

            else {
                return null;
            }
        }


        if(message.getCommand() == Command.SORT_ASC){
            return "SELECT * FROM " + message.getDatabase() + " ORDER BY "+ message.getArgument()+" ASC";
        }


        if(message.getCommand() == Command.SORT_DESC){
            return "SELECT * FROM " + message.getDatabase() + " ORDER BY "+ message.getArgument()+" DESC";
        }


        if (message.getCommand() == Command.DELETE) {
            return "DELETE FROM " + message.getDatabase() + " " + message.getArgument();
        }


        if (message.getCommand() == Command.UPDATE) {
            return "UPDATE " + message.getDatabase() + message.getArgument();
        }


        else {
            return null;
        }

    }

}
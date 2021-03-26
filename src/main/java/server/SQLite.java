package server;

import common.Books;
import common.OnLoan;
import common.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demonstrates how to make a connection to the SQLite database and then just
 * outputs the data to the console.
 *
 * @author Chris Bass
 */
public class SQLite {

    private String commandSQL;

    public static void main(String[] args) {

    }

    public void setCommandSQL(String newCommand){
        this.commandSQL = newCommand;
    }
    public String getCommandSQL(){
        return commandSQL;
    }


    /**
     * This will execute any query related to the database 'Books' in SQLite.
     * @return ArrayList
     */
    public synchronized List<Books> executeSQLCommandBooks() {
        ArrayList<Books> booksArrayList = new ArrayList<>();
        commandSQL = "SELECT * FROM Books";

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(commandSQL)) {

            ResultSet resultSet = prep.executeQuery();

            while (resultSet.next()) {
                booksArrayList.add(Books.newBookFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return booksArrayList;
    }

    /**
     * This will execute any query related to the database 'Person' in SQLite.
     * @return ArrayList
     */
    public synchronized List<OnLoan> executeSQLCommandOnLoan(){
        ArrayList<OnLoan> onLoanArrayList = new ArrayList<>();
        commandSQL = "SELECT * FROM on_loan";

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(commandSQL)) {

            ResultSet resultSet = prep.executeQuery();

            while (resultSet.next()) {
                onLoanArrayList.add(OnLoan.newLoanFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return onLoanArrayList;
    }

    /**
     * This will execute any query related to the database 'on_loan' in SQLite.
     * @return ArrayList
     */
    public synchronized List<Person> executeSQLCommandPerson(){
        ArrayList<Person> personArrayList = new ArrayList<>();
        commandSQL = "SELECT * FROM Person";
        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(commandSQL)) {

            ResultSet resultSet = prep.executeQuery();

            while (resultSet.next()) {
                personArrayList.add(Person.newPersonFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personArrayList;
    }


}
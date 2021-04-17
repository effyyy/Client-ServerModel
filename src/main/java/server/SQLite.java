package server;

import common.Publication;
import common.OnLoan;
import common.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demonstrates how to make a connection to the SQLite database and then just
 * outputs the data to the console.
 *
 * @author Chris Bass
 */
public class SQLite {

    public static void main(String[] args) {

    }

    /**
     * This will execute any query related to the database 'Publication' in SQLite.
     * @return ArrayList
     */
    public synchronized ArrayList<Publication> executeSQLCommandBooks(String sqlCommand) {
        ArrayList<Publication> publicationArrayList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(sqlCommand)) {

            ResultSet resultSet = prep.executeQuery();

            while (resultSet.next()) {
                publicationArrayList.add(Publication.newBookFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return publicationArrayList;
    }

    /**
     * This will execute any query related to the database 'Person' in SQLite.
     * @return ArrayList
     */
    public synchronized ArrayList<OnLoan> executeSQLCommandOnLoan(String sqlCommand){
        ArrayList<OnLoan> onLoanArrayList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(sqlCommand)) {

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
    public synchronized ArrayList<Person> executeSQLCommandPerson(String sqlCommand){
        ArrayList<Person> personArrayList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(sqlCommand)) {

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
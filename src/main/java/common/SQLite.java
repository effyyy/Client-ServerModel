package common;

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

    public SQLite(String commandSQL){
        this.commandSQL = commandSQL;
    }

    public static void main(String[] args) {

    }

    /**
     * Reads the first 10 records / rows from the tracks table in our SQLite database
     *
     * @return the first 10 tracks in the tracks table
     */
    public synchronized List<Books> executeSQLCommand() {
        ArrayList<Books> booksArrayList = new ArrayList<>();

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
    public synchronized List<OnLoan> executeSQLCommandP(){
        ArrayList<OnLoan> booksArrayList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(commandSQL)) {

            ResultSet resultSet = prep.executeQuery();

            while (resultSet.next()) {
                booksArrayList.add(OnLoan.newLoanFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return booksArrayList;
    }


}
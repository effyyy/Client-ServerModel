package server;

import common.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demonstrates how to make a connection to the SQLite database and then just
 * outputs the data to the console.
 *
 * @author Umair Afzal
 */
public class SQLite {

    /**
     * This will execute any query related to the database 'Publication' in SQLite.
     *
     * @return ArrayList
     */
    public synchronized ArrayList<Publication> executeSQLCommandBooks(String sqlCommand, Message message) {
        ArrayList<Publication> publicationArrayList = new ArrayList<>();


        if (message.getCommand() == Command.INSERT_INTO) {
            try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
                 PreparedStatement prep = conn.prepareStatement(sqlCommand)) {
                StringTokenizer st = new StringTokenizer(message.getArgument(), ",");
                prep.setString(1, st.nextToken());
                prep.setString(2, st.nextToken());
                prep.setDouble(3, Double.parseDouble(st.nextToken()));
                prep.setLong(4, Long.parseLong(st.nextToken()));
                prep.setLong(5, Long.parseLong(st.nextToken()));
                prep.setString(6, st.nextToken());
                prep.setInt(7, Integer.parseInt(st.nextToken()));
                prep.setDouble(8, Double.parseDouble(st.nextToken()));
                prep.setDouble(9, Double.parseDouble(st.nextToken()));
                prep.setInt(10, Integer.parseInt(st.nextToken()));
                int x = prep.executeUpdate();
                System.out.println(x + " Records Updated");

            } catch (SQLException ex) {
                Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
            return publicationArrayList;


        }
        if (message.getCommand() == Command.DELETE || message.getCommand() == Command.UPDATE) {
            try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
                 PreparedStatement prep = conn.prepareStatement(sqlCommand)) {
                int x = prep.executeUpdate();
                System.out.println(x + " Records Updated");
            } catch (SQLException ex) {
                Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
            return publicationArrayList;
        } else {

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
    }

    /**
     * This will execute any query related to the database 'Person' in SQLite.
     *
     * @return ArrayList
     */
    public synchronized ArrayList<OnLoan> executeSQLCommandOnLoan(String sqlCommand, Message message) {
        ArrayList<OnLoan> onLoanArrayList = new ArrayList<>();


        if (message.getCommand() == Command.INSERT_INTO) {
            try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
                 PreparedStatement prep = conn.prepareStatement(sqlCommand)) {
                StringTokenizer st = new StringTokenizer(message.getArgument(), ",");
                prep.setInt(1, Integer.parseInt(st.nextToken()));
                prep.setInt(2, Integer.parseInt(st.nextToken()));
                prep.setInt(3, Integer.parseInt(st.nextToken()));
                prep.setString(4, st.nextToken());
                prep.setString(5, st.nextToken());
                prep.setString(6, st.nextToken());
                prep.setString(7, st.nextToken());
                int x = prep.executeUpdate();
                System.out.println(x + " Records Updated");

            } catch (SQLException ex) {
                Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
            return onLoanArrayList;


        }
        if (message.getCommand() == Command.DELETE || message.getCommand() == Command.UPDATE) {
            try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
                 PreparedStatement prep = conn.prepareStatement(sqlCommand)) {
                int x = prep.executeUpdate();
                System.out.println(x + " Records Updated");
            } catch (SQLException ex) {
                Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
                 PreparedStatement prep = conn.prepareStatement(sqlCommand)) {

                ResultSet resultSet = prep.executeQuery();

                while (resultSet.next()) {
                    onLoanArrayList.add(OnLoan.newLoanFromResultSet(resultSet));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return onLoanArrayList;
    }

    /**
     * This will execute any query related to the database 'on_loan' in SQLite.
     *
     * @return ArrayList
     */
    public synchronized ArrayList<Person> executeSQLCommandPerson(String sqlCommand, Message message) {
        ArrayList<Person> personArrayList = new ArrayList<>();
        if (message.getCommand() == Command.INSERT_INTO) {
            try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
                 PreparedStatement prep = conn.prepareStatement(sqlCommand)) {
                StringTokenizer st = new StringTokenizer(message.getArgument(), ",");
                prep.setString(1, st.nextToken());
                prep.setString(2, st.nextToken());
                prep.setString(3, st.nextToken());
                int x = prep.executeUpdate();
                System.out.println(x + " Records Updated");

            } catch (SQLException ex) {
                Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
            return personArrayList;


        }
        if (message.getCommand() == Command.DELETE || message.getCommand() == Command.UPDATE) {
            try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
                 PreparedStatement prep = conn.prepareStatement(sqlCommand)) {
                int x = prep.executeUpdate();
                System.out.println(x + " Records Updated");
            } catch (SQLException ex) {
                Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
            return personArrayList;
        } else {
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


}
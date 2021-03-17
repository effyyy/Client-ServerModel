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

    public static void main(String[] args) {
        SQLite app = new SQLite();
        List<Track> trackList = app.read10();
        System.out.println("before: ");
        for (Track t : trackList) {
            System.out.println(t);
        }

        //app.deleteRecord();
        //app.insertRecord("my_composer_name");


        List<Track> trackList2 = app.read10();
        System.out.println("after: ");
        for (Track t : trackList2) {
            System.out.println(t);
        }
    }

    /**
     * Reads the first 10 records / rows from the tracks table in our SQLite database
     *
     * @return the first 10 tracks in the tracks table
     */
    public synchronized List<Track> read10() {
        ArrayList<Track> tracks = new ArrayList<>();
        String selectSQL = "SELECT * FROM tracks WHERE composer='AC/DC'"; // lets just get the first 10 records for testing

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {

            ResultSet resultSet = prep.executeQuery();

            while (resultSet.next()) {
                tracks.add(Track.newTrackFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tracks;
    }

    /**
     * Delete a single track
     */
    public synchronized void deleteRecord() {
        String deleteSQL = "DELETE FROM tracks WHERE Composer='AC/DC'";

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(deleteSQL)) {

            prep.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add a single track
     */
    public synchronized void insertRecord(Track track) {
        String insertSQL = "INSERT INTO tracks (Name, AlbumId, MediaTypeId, GenreId, Composer, Milliseconds, Bytes, UnitPrice) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) WHERE TrackId = ?";

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(insertSQL)) {

            prep.setString(1,track.getName());
//            prep.setInt(2,millisecs);
//            prep.setInt(3,bytes);
//            prep.setFloat(4,price);
//            prep.setInt();
            prep.execute();
            // okay recard added - update GUI
        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
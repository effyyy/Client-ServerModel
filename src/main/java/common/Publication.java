//Name :- Umair Afzal
//SID :- 8975414

package common;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a single row of data in the Book database table. Since there was already a Book class we renamed
 * the class to Publication in order to avoid confusion.
 */
public class Publication implements Serializable {
    private final int book_id; // primary key
    private final String title;
    private final String authors; // foriegn key
    private final double average_rating;
    private final long isbn;
    private final long isbn13;
    private final String language_code;
    private final int num_pages;
    private final double ratings_count;
    private final double text_reviews_count;
    private final int quantity;

    /**
     * This is the constructor for the publication class, parameters are the columns in the database and each input parameter
     * defines the value for that particular column in the database.
     */
    public Publication(int book_id, String title, String authors, double average_rating, long isbn, long isbn13, String language_code, int num_pages, double ratings_count, double text_ratings_count, int quantity) {
        this.book_id = book_id;
        this.title = title;
        this.authors = authors;
        this.average_rating = average_rating;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.language_code = language_code;
        this.num_pages = num_pages;
        this.ratings_count = ratings_count;
        this.text_reviews_count = text_ratings_count;
        this.quantity = quantity;
    }

    public static Publication newBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new Publication(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getDouble(4),
                resultSet.getLong(5),
                resultSet.getLong(6),
                resultSet.getString(7),
                resultSet.getInt(8),
                resultSet.getDouble(9),
                resultSet.getDouble(10),
                resultSet.getInt(11));
    }


    @Override
    public String toString() {
        return book_id +
                "," + title +
                "," + authors +
                "," + average_rating +
                "," + isbn +
                "," + isbn13 +
                "," + language_code +
                "," + num_pages +
                "," + ratings_count +
                "," + text_reviews_count +
                ", " + quantity;
    }

}
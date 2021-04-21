package common;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a single row of data in the database table.
 * @author Chris Bass
 */
public class Publication implements Serializable {
    private int book_id; // primary key
    private String title;
    private String authors; // foriegn key
    private double average_rating;
    private long isbn;
    private long isbn13;
    private String language_code;
    private int num_pages;
    private double ratings_count;
    private double text_reviews_count;
    private int quantity;

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

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(double average_rating) {
        this.average_rating = average_rating;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public long getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(long isbn13) {
        this.isbn13 = isbn13;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(int num_pages) {
        this.num_pages = num_pages;
    }

    public double getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(double ratings_count) {
        this.ratings_count = ratings_count;
    }

    public double getText_reviews_count(){return text_reviews_count;}

    public void setText_reviews_count(double text_reviews_count) { this.text_reviews_count = text_reviews_count; }

    public int getQuantity(){return quantity;}

    public void setQuantity(int quantity){ this.quantity = quantity;}

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
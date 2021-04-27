package common;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates an object for the persons database
 */
public class Person implements Serializable {

    private final String first_name;
    private final String last_name;
    private final int person_id;
    private final long library_card;

    /**
     * This is the constructor for the person class, parameters are the columns in the database and each input parameter
     * defines the value for that particular column in the database.
     * @param person_id
     * @param first_name
     * @param last_name
     * @param library_card
     */
    public Person(int person_id, String first_name, String last_name, long library_card) {
        this.person_id = person_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.library_card = library_card;
    }

    public static Person newPersonFromResultSet(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getLong(4));
    }

    public String toString() {
        return person_id + "," + first_name + "," + last_name + "," + library_card + ",";
    }


}

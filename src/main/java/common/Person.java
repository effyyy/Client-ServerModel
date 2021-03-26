package common;

import javax.xml.transform.Result;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person implements Serializable {

    private String first_name;
    private String last_name;
    private int person_id;
    private long library_card;

    public Person(int person_id, String first_name, String last_name, long library_card){
        this.person_id = person_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.library_card = library_card;
    }
    public static Person newPersonFromResultSet(ResultSet resultSet) throws SQLException{
        return new Person(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getLong(4));
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getPerson_id() {
        return person_id;
    }

    public long getLibrary_card() {
        return library_card;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setLibrary_card(long library_card) {
        this.library_card = library_card;
    }

    public String toString(){
        return "Person{"+
                " person_id = " + person_id+
                " First Name = " + first_name+
                " Last Name = " + last_name+
                " Library Card = "+ library_card+"}";
    }


}

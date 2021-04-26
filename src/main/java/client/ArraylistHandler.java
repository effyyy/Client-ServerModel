package client;

import common.Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ArraylistHandler implements Serializable {


    private final ArrayList<?> arrayList;
    private final String[] columnNameBooks = {"book_id"
            , "title", "authors", "average_rating", "isbn", "isbn13", "language_code", "num_pages", "ratings_count",
            "text_ratings_count", "quantity"};

    //Column Names for all databases
    private final String[] columnNamePersons = {"person_id", "first_name", "last_name", "library_card"};
    private final String[] columnNameOnLoan = {"loan_id", "book_id", "+person_id", "loan_period", "loan_start",
            "loan_end", "returned_date", "return_status"};
    private Database database;


    public ArraylistHandler(Database database, ArrayList<?> arrayList) {
        this.arrayList = arrayList;
        this.database = database;
    }

    /**
     * Returns the column names for the specified database. Takes a count as a parameter for the column number.
     *
     * @param count used to specify what column we need.
     * @return Returns a String
     */
    public String getColumnName(int count) {
        if (database == Database.BOOKS) {
            return columnNameBooks[count];
        }
        if (database == Database.PERSON) {

            return columnNamePersons[count];
        }
        if (database == Database.ON_LOAN) {

            return columnNameOnLoan[count];
        } else {
            return null;
        }
    }

    public String[] getColumnArray() {
        if (database == Database.BOOKS) {
            return columnNameBooks;
        }
        if (database == Database.PERSON) {

            return columnNamePersons;
        }
        if (database == Database.ON_LOAN) {

            return columnNameOnLoan;
        } else {
            return null;
        }
    }

    public int getColumnLength() {
        if (database == Database.BOOKS) {
            return columnNameBooks.length;
        }
        if (database == Database.PERSON) {
            return columnNamePersons.length;
        }
        if (database == Database.ON_LOAN) {
            return columnNameOnLoan.length;
        } else {
            return 0;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<Object> tempArraylist = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(arrayList.get(rowIndex).toString());
        while (st.hasMoreTokens()) {
            tempArraylist.add(st.nextToken(","));
        }
        return tempArraylist.get(columnIndex);
    }


    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }


    public int getArraySize() {
        return arrayList.size();
    }


}

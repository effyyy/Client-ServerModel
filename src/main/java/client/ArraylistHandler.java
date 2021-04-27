package client;

import common.Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class handles Arraylist of rows which are used to plot Jtables, this Class uses a String tokenizer in order to
 * handle all types of databases as long as they have a common toString function. It is also responsible for returning
 * columnNames and cell values to the GenericTableModel
 */
public class ArraylistHandler implements Serializable {


    private final ArrayList<?> arrayList;

    private Database database;



    //Column Names for all databases
    private final String[] columnNameBooks = {"book_id"
            , "title", "authors", "average_rating", "isbn", "isbn13", "language_code", "num_pages", "ratings_count",
            "text_ratings_count", "quantity"};

    private final String[] columnNamePersons = {"person_id", "first_name", "last_name", "library_card"};

    private final String[] columnNameOnLoan = {"loan_id", "book_id", "+person_id", "loan_period", "loan_start",
            "loan_end", "returned_date", "return_status"};


    /**
     * Constructor for the ArraylistHandler Class
     * @param database The database which we need to operate on
     * @param arrayList ArrayList of Objects, each object represents a row of the database
     */
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


    /**
     * Returns an array of Column Names depending on the database used in the constructor
     * @return String[], this contains all the column names for the selected database
     */
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



    /**
     * @return Returns the length of the ColumnName array for the selected Database, this is useful in plotting Jtables
     */
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


    /**
     * This method gives the value of a particular cell in the Jtable, used for plotting the Jtable
     * @param rowIndex The row index of the cell we want the data for
     * @param columnIndex The column index of the cell we want the data for
     * @return Returns the object at the desired Cell location
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<Object> tempArraylist = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(arrayList.get(rowIndex).toString());
        while (st.hasMoreTokens()) {
            tempArraylist.add(st.nextToken(","));
        }
        return tempArraylist.get(columnIndex);
    }


    /**
     * @return Returns the selected Database
     */
    public Database getDatabase() {
        return database;
    }


    /**
     * @param database Takes a Database enum as an input , sets the Database for the ArraylistHandler
     */
    public void setDatabase(Database database) {
        this.database = database;
    }


    /**
     * @return Returns the size of the Arraylist, this essentially is the row count for the JTable
     */
    public int getArraySize() {
        return arrayList.size();
    }


}

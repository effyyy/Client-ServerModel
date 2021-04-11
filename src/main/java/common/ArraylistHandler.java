package common;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ArraylistHandler {


    private ArrayList<?>arrayList;

    private Database database;

    //Column Names for all databases
    String[] columnNameBooks = {"Book ID","Title","Authors","Average Rating","ISBN","ISBN 13","Language Code","Num Pages","Rating Count","Quantity"};
    String[] columnNamePersons = {"First Name", "Last Name", " Person ID" , "Library Card"};
    String[] columnNameOnLoan ={"Loan ID", "Person ID","Book ID", "Loan Period","Loan Start","Loan End","Returned Date","Return Status"};

    public ArraylistHandler(Database database, ArrayList<?> arrayList){
        this.arrayList = arrayList;
        this.database = database;
    }

    /**
     * Returns the column names for the specified database. Takes a count as a parameter for the column number.
     * @param count used to specify what column we need.
     * @return
     */
    public String getColumnNames(int count){
        if(database == Database.BOOKS){
            return columnNameBooks[count];
        }
        if(database== Database.PERSONS){

            return columnNamePersons[count];
        }
        if(database == Database.ON_LOAN){

            return columnNameOnLoan[count];
        }
        else{
            return null;
        }
    }
    public int getColumnLength(){
        if(database==Database.BOOKS){
            return columnNameBooks.length;
        }
        if(database==Database.PERSONS){
            return columnNamePersons.length;
        }
        if(database==Database.ON_LOAN){
            return columnNameOnLoan.length;
        }
        else{
            return 0;
        }
    }
    public Object getValueAt(int rowIndex,int columnIndex){
        ArrayList<Object>tempArraylist = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(arrayList.get(rowIndex).toString());
        while(st.hasMoreTokens()){
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


    public ArrayList<?> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<?> arrayList) {
        this.arrayList = arrayList;
    }
    public int getArraySize(){
        return arrayList.size();
    }


}

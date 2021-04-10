package common;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class TableModelPerson extends AbstractTableModel {

    private String[] columnNames = {"First Name", "Last Name", " Person ID" , "Library Card"};
    ArrayList<Person> personArrayList;
    ArrayList<Books> booksArrayList;
    ArrayList<OnLoan> onLoanArrayList;
    Database database;
    public TableModelPerson(ArrayList<Person> arrayList){
        personArrayList = arrayList;
        this.database = database;
    }


    @Override
    public int getRowCount() {
        return personArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
           return personArrayList.get(rowIndex).getFirst_name();
        }
        if(columnIndex == 1){
           return personArrayList.get(rowIndex).getLast_name();
        }
        if(columnIndex == 2){
           return personArrayList.get(rowIndex).getPerson_id();
        }
        if(columnIndex == 3){
           return personArrayList.get(rowIndex).getLibrary_card();
        }
        return 0;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void addToTable(Object aValue){
    }
}

package common;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class GenericTableModel extends AbstractTableModel {
    Database database;
    ArrayList<?> arrayList;
    public GenericTableModel(ArrayList<?> arrayList, Database database){
        this.database = database;
        this.arrayList = arrayList;
    }


    @Override
    public int getRowCount() {
        ArraylistHandler arraylistHandler = new ArraylistHandler(database, arrayList);
        return arraylistHandler.getArraySize();
    }

    @Override
    public int getColumnCount() {
        ArraylistHandler arraylistHandler = new ArraylistHandler(database, arrayList);
        return arraylistHandler.getColumnLength();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArraylistHandler arraylistHandler = new ArraylistHandler(database, arrayList);
       return arraylistHandler.getValueAt(rowIndex,columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        ArraylistHandler arraylistHandler = new ArraylistHandler(database, arrayList);
        return arraylistHandler.getColumnNames(column);
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

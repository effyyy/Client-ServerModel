package client;

import common.Database;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class GenericTableModel extends AbstractTableModel {
    Database database;
    ArrayList<?> arrayList;
    ArraylistHandler arraylistHandler;

    public GenericTableModel(ArrayList<?> arrayList, Database database) {
        this.database = database;
        this.arrayList = arrayList;
        this.arraylistHandler = new ArraylistHandler(database, arrayList);
    }


    @Override
    public int getRowCount() {
        return arraylistHandler.getArraySize();
    }


    @Override
    public int getColumnCount() {
        return arraylistHandler.getColumnLength();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return arraylistHandler.getValueAt(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return arraylistHandler.getColumnName(column);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

}

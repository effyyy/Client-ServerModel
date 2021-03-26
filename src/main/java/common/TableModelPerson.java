package common;

import javax.swing.table.AbstractTableModel;


public class TableModelPerson extends AbstractTableModel {

    private String[] columnNames = {"First Name", "Last Name", " Person ID" , "Library Card"};
    private String[][] data;


    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] =   aValue.toString();
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void addToTable(Object aValue){
    }
}

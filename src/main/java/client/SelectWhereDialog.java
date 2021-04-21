package client;

import common.Database;

import javax.swing.*;
import java.awt.event.*;

public class SelectWhereDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> columnDropDown;
    private JComboBox operationDropDown;
    private JTextField textField1;
    private JLabel labelMessage;
    public static String whereClause;



    public SelectWhereDialog(Database database) {
        //Getting Column Names for the ComboBox
        ArraylistHandler handler = new ArraylistHandler(database,null);
        String[] columnNames = handler.getColumnArray();
        //Setting the GUI specifics
        setContentPane(contentPane);
        setSize(500,300);
        setVisible(true);
        setAlwaysOnTop(true);
        setTitle("Where Statement Creator");

        //Setting Drop Down Menus
        for(String x: columnNames)
        columnDropDown.addItem(x);


        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(onOK());
                ClientGUI.argument = onOK();
            }
        });
    }

    private String onOK() {
        // add your code here
        String inputColumn = (String) columnDropDown.getSelectedItem();
        String inputOperation = (String) operationDropDown.getSelectedItem();
        String toReturn =inputColumn+" "+inputOperation+"  '"+textField1.getText()+"'";
        dispose();
        return toReturn;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public String getWhereClause(){
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        SelectWhereDialog.whereClause = whereClause;
    }

    public static void main(String[] args) {
    }
}

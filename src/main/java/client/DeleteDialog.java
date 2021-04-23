package client;

import common.Database;

import javax.swing.*;
import java.awt.event.*;

public class DeleteDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> dropDownMenu;
    private JTextField toDeleteField;
    private JComboBox operationDropDown;

    public DeleteDialog(Database database) {
        setTitle("Delete Dialog");
        ArraylistHandler handler = new ArraylistHandler(database,null);
        String[] columnNames = handler.getColumnArray();
        for (String x:columnNames) {
            dropDownMenu.addItem(x);
        }
        setContentPane(contentPane);
        setVisible(true);
        setAlwaysOnTop(true);
        pack();
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ClientGUI.argument = onOK();
            }
        });

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
    }

    private String onOK() {
        // add your code here
        String toReturn = "WHERE "+ dropDownMenu.getSelectedItem() + operationDropDown.getSelectedItem()+toDeleteField.getText();
        dispose();
        return toReturn;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
    }
}

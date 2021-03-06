//Name :- Umair Afzal
//SID :- 8975414

package client;

import common.Database;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DeleteDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> dropDownMenu;
    private JTextField toDeleteField;
    private JComboBox <String>operationDropDown;

    /**
     *  Creates a Dialog for the input of argument for the DELETE Function based on the database selected
     * @param database the selected database, this decides what column names we input as options
     */
    public DeleteDialog(Database database) {
        setTitle("Delete Dialog");
        ArraylistHandler handler = new ArraylistHandler(database, null);
        String[] columnNames = handler.getColumnArray();
        for (String x : columnNames) {
            dropDownMenu.addItem(x);
        }
        setContentPane(contentPane);
        setVisible(true);
        setAlwaysOnTop(true);
        pack();
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> ClientGUI.argument = onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
    }

    private String onOK() {
        // creates an argument based on the input from the user
        String toReturn = "WHERE " + dropDownMenu.getSelectedItem() + operationDropDown.getSelectedItem() +
                toDeleteField.getText();
        dispose();
        return toReturn;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

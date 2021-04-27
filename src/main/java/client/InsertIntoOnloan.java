//Name :- Umair Afzal
//SID :- 8975414

package client;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InsertIntoOnloan extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;


    /**
     * Creates a Dialog for the input of argument for the Insert Function for the On_loan Database
     */
    public InsertIntoOnloan() {
        setContentPane(contentPane);
        setTitle("Insert into the ONLOAN database");
        pack();
        setVisible(true);
        setAlwaysOnTop(true);
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
        InsertIntoOnloan dialog = new InsertIntoOnloan();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private String onOK() {
        // add your code here
        String toReturn = textField1.getText() + "," + textField2.getText() + "," +
                textField3.getText() + "," + textField4.getText() + "," + textField5.getText() + "," + textField6.getText() + "," +
                textField7.getText();
        dispose();
        return toReturn;

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

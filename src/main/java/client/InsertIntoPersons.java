//Name :- Umair Afzal
//SID :- 8975414

package client;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InsertIntoPersons extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;


    /**
     * Creates a Dialog for the input of argument for the Insert Function for the Person Database
     */
    public InsertIntoPersons() {
        setTitle("INSERT INTO Dialog Box");
        setContentPane(contentPane);
        pack();
        setVisible(true);
        setAlwaysOnTop(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            System.out.println(onOK());
            ClientGUI.argument = onOK();
        });

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
        InsertIntoPersons dialog = new InsertIntoPersons();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private String onOK() {
        // add your code here
        String toReturn = textField2.getText() + "," + textField3.getText() + "," + textField4.getText();
        dispose();
        return toReturn;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

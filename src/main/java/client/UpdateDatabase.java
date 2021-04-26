package client;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UpdateDatabase extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;

    public UpdateDatabase() {
        setTitle("Update Database Dialog");
        setContentPane(contentPane);
        setSize(500, 300);
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
        UpdateDatabase dialog = new UpdateDatabase();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private String onOK() {
        // add your code here
        String toReturn = " SET " + textField1.getText() + " WHERE " + textField2.getText();
        dispose();
        return toReturn;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

package client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientGUI extends JFrame {
    JButton sqlExecute;
    JTextField textFieldEmail;
    JButton connectButton;
    JLabel labelStatus;
    Client client = new Client();
    private JPanel guiPanel;
    private JLabel displayLabel;
    private String test = "Please insert first name";

    ClientGUI() {

        this.setContentPane(guiPanel);
        this.setSize(700, 300);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        this.setTitle("Library Book Manager");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
                client.closeConnection(labelStatus);
                System.exit(0);
            }
        });

        connectButton.addActionListener(evt -> client.reconnectToServer(labelStatus));

        sqlExecute.addActionListener(e -> {
             String tableSelect = JOptionPane.showInputDialog("Please Select Table Number : \n 1: Books \n 2:Person Record \n 3: Loan Status");
             String textToSend = "SQLExecute : " + tableSelect;
             client.sendToServer(textToSend,displayLabel);
        });
    }

    public static void main(String[] args) {
        ClientGUI gui = new ClientGUI();
    }

}


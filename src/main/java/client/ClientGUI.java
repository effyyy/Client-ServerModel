package client;

import common.OnLoan;
import common.SQLite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ClientGUI extends JFrame{
    JButton sqlExecute;
    JTextField textFieldEmail;
     JTextField sqlcommandIN;
     JButton connectButton;
     JLabel labelStatus;
    private JPanel guiPanel;
    private JLabel timeLabel;

    Client client = new Client();

    ClientGUI(){

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

        connectButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                client.reconnectToServer(labelStatus);
            }

        });
        sqlExecute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sqliteCommand = sqlcommandIN.getText();
                SQLite app = new SQLite(sqliteCommand);
                List<OnLoan> trackList = app.executeSQLCommandP();
                for (OnLoan t : trackList) {
                    System.out.println(t);
                }
            }
        });
    }

    public static void main(String[] args) {
        ClientGUI gui = new ClientGUI();
    }

}


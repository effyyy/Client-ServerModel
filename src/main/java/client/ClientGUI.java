package client;

import common.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ClientGUI extends JFrame {
    private JButton SelectTable;
    private JButton connectButton;
    public  JLabel displayLabel;
    private JPanel guiPanel;
    private JTable sqlTable;
    private JButton createTable;
    private JScrollPane scroller;
    private JList databaseSelect;
    private JList crudSelect;
    private JButton confirmSelect;
    Client client = new Client();

    ClientGUI() {
        this.setContentPane(guiPanel);
        this.setSize(300, 300);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        this.setTitle("Library Book Manager");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
                client.closeConnection();
                System.exit(0);
            }
        });

        connectButton.addActionListener(evt -> client.reconnectToServer());

        confirmSelect.addActionListener(evt -> {
            int selectedIndex = databaseSelect.getSelectedIndex();
            Database database = getDatabase(selectedIndex);
            Message toSend = new Message(Command.SELECT, database);
            client.sendToServer(toSend);
            client.ReadFromServer();
            plotGraph(client.toPlot,(client.toSend).getDatabase());
        });
    }

    public void plotGraph (ArrayList<?> arrayList, Database database){
        GenericTableModel genericModel = new GenericTableModel(arrayList, database);
        sqlTable.setModel(genericModel);
    }

    private Database getDatabase(int input) {
        if (input == 0) {
            return Database.BOOKS;
        }
        if (input == 1) {
            return Database.PERSONS;
        }
        if (input == 2) {
            return Database.ON_LOAN;
        }
        else{
            setDisplayLabel("Please Select an input");
            return null;
        }
    }

    public void setDisplayLabel(String say){
        displayLabel.setText("Status : "+ say);
    }

    public static void main(String[] args){
    }

}


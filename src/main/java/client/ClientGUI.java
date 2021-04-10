package client;

import common.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ClientGUI extends JFrame {
    private JButton sqlExecute;
    private JButton connectButton;
    public  JLabel displayLabel;
    private JPanel guiPanel;
    private JTable sqlTable;
    private JButton createTable;
    private JScrollPane scroller;
    Client client = new Client();

    ClientGUI() {
        this.setContentPane(guiPanel);
        this.setSize(1280, 786);
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

        sqlExecute.addActionListener(evt -> {
             //String  input = JOptionPane.showInputDialog("Please Select Table Number : \n 1: Books \n 2:Person Record \n 3: Loan Status");
             Message toSend = new Message(Command.SELECT, Database.PERSONS);
             client.sendToServer(toSend);
        });

        createTable.addActionListener(evt -> {
            client.ReadFromServer();
            plotGraph(client.toPlot,(client.toSend).getDatabase());
        });
    }

    public void plotGraph (ArrayList<Person> arrayList, Database database){
        TableModelPerson genericModel = new TableModelPerson(arrayList);
        sqlTable.setModel(genericModel);
    }

    public void setDisplayLabel(String say){
        displayLabel.setText("Status : "+ say);
    }

    public static void main(String[] args){
    }

}


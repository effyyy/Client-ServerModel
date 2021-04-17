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
    private JList selectFunctionality;
    private JButton confirmSelect;
    private JButton updateTable;
    private Message toSend;
    public static String argument;

    Client client = new Client();

    ClientGUI() {
        this.setContentPane(guiPanel);

        this.setSize(1280,800);

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

            //Selecting Database from JLIST
            Database database = getDatabase(databaseSelect.getSelectedIndex());

            //Selecting Command from JLIST
            Command command = getCommand(selectFunctionality.getSelectedIndex(),database);

            //Sending the selected Message object to the Server
            toSend = new Message(command, database,argument);
            client.sendToServer(toSend);

            //Reading the reply from the server and then using it to plot a graph on the GUi
            client.ReadFromServer();
            plotGraph(client.toPlot,database);

        });


        updateTable.addActionListener(e -> {

            connectButton.doClick();

            confirmSelect.doClick();

        });
    }

    public void plotGraph (ArrayList<?> arrayList, Database database){
        GenericTableModel genericTableModel = new GenericTableModel(arrayList, database);
        sqlTable.setModel(genericTableModel);
    }

    public void plotEmptyGraph(Database database){
        ArrayList<?> arrayList = new ArrayList<>();
        GenericTableModel genericTableModel = new GenericTableModel(arrayList,database);
        sqlTable.setModel(genericTableModel);
    }

    private Database getDatabase(int input) {
        if (input == 0) {
            return Database.BOOKS;
        }
        if (input == 1) {
            return Database.PERSON;
        }
        if (input == 2) {
            return Database.ON_LOAN;
        }
        else{
            setDisplayLabel("Please Select an input");
            return null;
        }
    }

    private Command getCommand(int input, Database database){
        if(input == 0){
            return Command.SELECT_ALL;
        }
        if(input == 1){
            SelectWhereDialog selectWhereDialog = new SelectWhereDialog(database);
            return Command.SELECT_WHERE;

        }
        if(input == 3){
            return Command.UPDATE;
        }
        if(input == 4){
            return Command.DELETE;
        }
        else{
            return null;
        }
    }

    public void setDisplayLabel(String say){

        displayLabel.setText("Status : "+ say);
    }

    public String[] getColumnNames(Database database){
            ArraylistHandler arraylistHandler = new ArraylistHandler(database, null);
            return arraylistHandler.getColumnArray();
    }



    public static void main(String[] args){
    }

}

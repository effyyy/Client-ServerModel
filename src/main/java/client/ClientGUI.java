package client;

import common.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ClientGUI extends JFrame {

    private JButton connectButton;

    public  JLabel displayLabel;

    private JPanel guiPanel;

    private JTable sqlTable;

    private JScrollPane scroller;

    private JList databaseSelect;

    private JList selectFunctionality;

    private JButton confirmSelect;

    private JButton updateTableButton;

    private JButton printTableButton;

    private JButton clearTableButton;

    private JButton plotTableButton;

    private JButton sendToServerButton;

    private Message toSend;

    public static String argument;

    public Database database;

    private Command command;

    private ArrayList<?> toPlot;

    private static String testString;



    Client client = new Client();

    ClientGUI() {
        this.setContentPane(guiPanel);

        this.setSize(1440,600);

        this.setVisible(true);

        this.setAlwaysOnTop(true);

        this.setTitle("Library Book Manager");


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.

                setDisplayLabel(client.closeConnection());

                System.exit(0);

            }
        });

        displayLabel.setText(testString);


        connectButton.addActionListener(evt ->
                setDisplayLabel(client.reconnectToServer()));



        confirmSelect.addActionListener(evt -> {

            //Selecting Database from JLIST
            database = getDatabase(databaseSelect.getSelectedIndex());

            //Selecting Command from JLIST
            command = getCommand(selectFunctionality.getSelectedIndex(),database);

            getArgument();

            setDisplayLabel("You have selected the Database :"+database+", Command :" + command +" Argument:"+ argument);

            //Check if we are clear for sending the selected Message object to the Server

        });


        updateTableButton.addActionListener(e -> {

        });

        plotTableButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(command == Command.INSERT_INTO || command == Command.DELETE){

                    Message message =  new Message(Command.SELECT_ALL,database,null);

                    client.sendToServer(message);

                }
                setDisplayLabel(client.readFromServer());

                toPlot = client.toPlot;

                plotGraph();

            }
        });
        sendToServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toSend = new Message(command, database, argument);
                setDisplayLabel(client.sendToServer(toSend));
                ClientGUI.argument = null;
            }
        });
    }

    public void plotGraph(){
            if (toPlot != null && database != null) {
                GenericTableModel genericTableModel = new GenericTableModel(toPlot, database);
                sqlTable.setModel(genericTableModel);
            }
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
            return Command.SELECT_WHERE;
        }
        if(input == 2){
            return Command.INSERT_INTO;
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

    public void getArgument(){
        if(command == Command.SELECT_WHERE) {
            SelectWhereDialog selectWhereDialog = new SelectWhereDialog(database);
        }
        if(command == Command.INSERT_INTO){
            if(database == Database.PERSON) {
                InsertIntoPersons insertIntoPersons = new InsertIntoPersons();
            }
            if(database == Database.BOOKS){
                InsertIntoBooks insertIntoBooks = new InsertIntoBooks();
            }
            if(database==Database.ON_LOAN){
                InsertIntoOnloan insertIntoOnloan = new InsertIntoOnloan();
            }
        }
        if(command == Command.DELETE){
            DeleteDialog deleteDialog = new DeleteDialog(database);
        }
    }

    public void setDisplayLabel(String say) {
       displayLabel.setText(say);
    }


    public static void main(String[] args){
    }

}

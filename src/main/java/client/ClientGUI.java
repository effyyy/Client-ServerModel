package client;

import common.Command;
import common.Database;
import common.Message;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.util.*;

public class ClientGUI extends JFrame {

    public static String argument;
    public JLabel displayLabel;

    public JPanel guiPanel;
    public Database database;
    Client client = new Client();
    private JButton connectButton;
    private JTable sqlTable;
    private JList<String> databaseSelect;
    private JList<String> selectFunctionality;
    private JButton confirmSelect;
    private JButton printTableButton;
    private JButton clearTableButton;
    private JButton sendToServerButton;
    private Message toSend;
    private Command command;
    GenericTableModel genericTableModel;

    public ClientGUI() {
        this.setContentPane(guiPanel);

        this.setSize(1440, 600);

        this.setVisible(true);

        this.setAlwaysOnTop(true);

        this.setTitle("Library Book Manager");


        //Populating the J-list for Databases (We do it this way to accommodate further Newly created databases)



        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.

                setDisplayLabel(client.closeConnection());

                System.exit(0);

            }
        });

        connectButton.addActionListener(evt ->
                setDisplayLabel(client.reconnectToServer()));


        confirmSelect.addActionListener(evt -> {

            //Selecting Database from JLIST
            database = getDatabase(databaseSelect.getSelectedIndex());

            //Selecting Command from JLIST
            command = getCommand(selectFunctionality.getSelectedIndex());

            getArgument();


            //Check if we are clear for sending the selected Message object to the Server

        });


        sendToServerButton.addActionListener(e -> {
            toSend = new Message(command, database, argument);
            setDisplayLabel(client.sendToServer(toSend));
            ClientGUI.argument = null;
            plotGraph();
        });
        printTableButton.addActionListener(e -> {
            try {
                boolean complete = sqlTable.print();
                if (complete) {
                    setDisplayLabel("Table Print Successful");
                } else {
                    setDisplayLabel("Table Printing cancelled");
                }
            } catch (PrinterException pe) {
                setDisplayLabel("Failed to Print Table");
            }
        });
        clearTableButton.addActionListener(e -> {
            DefaultTableModel emptyModel = new DefaultTableModel();
            emptyModel.setRowCount(0);
            sqlTable.setModel(emptyModel);
        });
    }

    public static void main(String[] args) {
    }

    public void plotGraph() {

        client.readFromServer();
        ArrayList<?> toPlot = client.toPlot;
        genericTableModel = new GenericTableModel(toPlot, database);
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
        } else {
            setDisplayLabel("Please Select an input");
            return null;
        }
    }

    private Command getCommand(int input) {
        if (input == 0) {
            return Command.SELECT_ALL;
        }
        if (input == 1) {
            return Command.SELECT_WHERE;
        }
        if (input == 2) {
            return Command.INSERT_INTO;
        }
        if (input == 3) {
            return Command.UPDATE;
        }
        if (input == 4) {
            return Command.DELETE;
        }
        if(input == 5){
            return Command.SORT_ASC;
        }
        if(input == 6){
            return Command.SORT_DESC;
        }
        else {
            return null;
        }
    }

    public void getArgument() {

        ArraylistHandler arraylistHandler = new ArraylistHandler(database,null);
        String[] myArray = arraylistHandler.getColumnArray();

        if (command == Command.SELECT_WHERE) {
            new SelectWhereDialog(database);
        }
        if (command == Command.INSERT_INTO) {
            if (database == Database.PERSON) {
                new InsertIntoPersons();
            }
            if (database == Database.BOOKS) {
                new InsertIntoBooks();
            }
            if (database == Database.ON_LOAN) {
                new InsertIntoOnloan();
            }
        }
        if (command == Command.DELETE) {
            new DeleteDialog(database);
        }
        if (command == Command.UPDATE) {
            new UpdateDatabase();
        }
        if(command == Command.SORT_ASC){
            ClientGUI.argument = (String) JOptionPane.showInputDialog(guiPanel,"Please Select the column to sort by",
                    "Sort Table Ascending order",JOptionPane.PLAIN_MESSAGE,null,myArray,0);
        }
        if(command == Command.SORT_DESC){
            ClientGUI.argument = (String)JOptionPane.showInputDialog(guiPanel,"Please Select the column to sort by",
                    "Sort Table Descending order",JOptionPane.PLAIN_MESSAGE,null,myArray,0);
        }
    }

    public void setDisplayLabel(String say) {
        displayLabel.setText(say);
    }

}

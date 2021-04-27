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

/**
 * This class is responsible for creating the GUI for our App, this class takes care of how the user interacts with
 * the client and the database. The input of data in a restricted format to prevent the user from begin able to enter
 * SQL statements. On this flip side, it provides a relatively simple functionality without having to write complex SQL
 * statements.
 */
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

    /**
     * Constructor for the ClientGUI,
     * Sets the specifics for our GUI such as Visibility and Titles etc.., it also is home to all the action listeners
     * for the buttons we have in the GUI.
     */
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
                //Executes the send to server command and returns what is
                // sent to the server
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
            toSend = new Message(command, database, argument);  //Creating a Message object using the selected databse and command

            setDisplayLabel(client.sendToServer(toSend));//Sending to server and output the result to status bar

            ClientGUI.argument = null; //reset the 'argument' field

            plotGraph();//Plots the graph for while reading the server

        });
        printTableButton.addActionListener(e -> {

            //Prints the currently displayed table. (Opens a print window for specifics)
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

            //creates an empty tale model and sets it as the sqlTable's table model
            DefaultTableModel emptyModel = new DefaultTableModel();

            emptyModel.setRowCount(0);

            sqlTable.setModel(emptyModel);

        });
    }

    public void plotGraph() {

        client.readFromServer();

        ArrayList<?> toPlot = client.toPlot;

        genericTableModel = new GenericTableModel(toPlot, database);

        sqlTable.setModel(genericTableModel);

    }


    /**
     * @param input Input is the J-list entry (databaseSelect) index which is currently selected.
     * @return
     */
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

    /**
     * @param input Input is the J-list entry index(selectFunctionality) which is currently selected.
     * @return  returns the command according to the J-List entry currently selected
     */
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

    /**
     * This function sets the 'argument' for the message object, this functions uses different methods to obtain the
     * argument depending on the command and database selected.
     */
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

    /**
     * @param say The input string which is then printed onto the Status Bar
     */
    public void setDisplayLabel(String say) {
        displayLabel.setText(say);
    }

}

package common;

import java.io.Serializable;

public class Message implements Serializable {

    Command command;
    Database database;
    String argument;

    public Message(Command command, Database database, String argument) {
        this.command = command;
        this.database = database;
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {

        this.command = command;
    }

    public Database getDatabase() {

        return database;
    }

    public void setDatabase(Database database) {

        this.database = database;
    }

    public String toString() {
        return "Selected Command is " + command +
                " Selected Database is " + database + " Inserted Argument is : " + argument;
    }
}

//Name :- Umair Afzal
//SID :- 8975414

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


    public Command getCommand() {
        return command;
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

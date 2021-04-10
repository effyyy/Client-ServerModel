package common;

import java.io.Serializable;

public class Message implements Serializable {
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

    Command command;
    Database database;

    public Message(Command command, Database database){
        this.command = command;
        this.database = database;
    }
    public String toString(){
        return "Selected Command is " + command+
                " Selected Database is "+ database;
    }
}

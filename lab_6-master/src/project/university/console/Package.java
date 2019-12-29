package project.university.console;

import project.university.console.Author;

import java.io.Serializable;

public class Package implements Serializable {
    Author author;
    String command;
    public Package(Author author, String command){
        this.author = author;
        this.command = command;
    }

    public Author getAuthor() {
        return author;
    }

    public String command(){
        return command;
    }
}

package main.java;

import main.java.views.CommandLineHandler.CommandLineHandler;

import java.io.InvalidObjectException;

public class Main {
    public static void main(String[] args) {
        try {
            CommandLineHandler commandLineHandler = new CommandLineHandler();
            commandLineHandler.start();
        } catch (InvalidObjectException exception) {
            exception.printStackTrace();
        }
    }
}

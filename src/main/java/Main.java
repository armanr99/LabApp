package main.java;

import main.java.views.CommandLineHandler.CommandLineHandler;

import java.io.InvalidObjectException;

public class Main {
    public static void main(String[] args) throws InvalidObjectException {
        CommandLineHandler commandLineHandler = new CommandLineHandler();
        commandLineHandler.start();
    }
}

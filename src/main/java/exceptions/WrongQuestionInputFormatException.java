package main.java.exceptions;

public class WrongQuestionInputFormatException extends Exception {
    public String toString() {
        return "Wrong input format detected for question input";
    }
}

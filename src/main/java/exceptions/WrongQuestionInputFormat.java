package main.java.exceptions;

public class WrongQuestionInputFormat extends Exception {
    public String toString() {
        return "Wrong input format detected for question input";
    }
}

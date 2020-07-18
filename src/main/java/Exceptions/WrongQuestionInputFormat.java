package main.java.Exceptions;

public class WrongQuestionInputFormat extends Exception {
    public String toString() {
        return "Wrong input format detected for question type";
    }
}

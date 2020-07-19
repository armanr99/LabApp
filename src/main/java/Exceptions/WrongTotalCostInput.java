package main.java.Exceptions;

public class WrongTotalCostInput extends Exception {
    public String toString() {
        return "Total cost input doesn't match with corresponding total cost";
    }
}

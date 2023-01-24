package exceptions;

public class IncorrectArgumentException extends Exception {
    private final String argument;
    private final String message;

    public IncorrectArgumentException(String message, String argument) {
        super(message);
        this.argument = argument;
        this.message = message;
    }


    public String getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return message + " Аргумент: " + argument;
    }


}

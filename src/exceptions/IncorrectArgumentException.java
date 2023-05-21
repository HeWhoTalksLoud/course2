package exceptions;

public class IncorrectArgumentException extends Exception {
    private final String argument;
    private final String message;

    public IncorrectArgumentException(String message, String argument) {
        super(message);
        this.argument = argument;
        this.message = message;
    }

    public IncorrectArgumentException(String message, Throwable cause, String argument) {
        super(message, cause);
        this.argument = argument;
        this.message = message;
    }

    public IncorrectArgumentException(Throwable cause) {
        super(cause);
        this.argument = "";
        this.message = "";
    }

    public String getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return message + "\nАргумент: " + argument;
    }


}

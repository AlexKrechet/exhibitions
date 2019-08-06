package ua.andrii.project_7.exception;

public class WrongShowroomDataException extends Exception {
    public WrongShowroomDataException() {
        super();
    }

    public WrongShowroomDataException(String message) {
        super(message);
    }
}

package ua.goit.projectmanagementsystem.exception;

public class ProjectAlreadyExistException extends RuntimeException{

    public ProjectAlreadyExistException(String message) {
        super (message);
    }
}

package ua.goit.projectmanagementsystem.exception;

public class CompanyAlreadyExistException extends RuntimeException{

    public CompanyAlreadyExistException(String message) {
        super (message);
    }
}

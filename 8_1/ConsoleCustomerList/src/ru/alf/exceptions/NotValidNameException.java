package ru.alf.exceptions;

public class NotValidNameException extends RuntimeException {

    public NotValidNameException(String message){
        super(message);
    }
}

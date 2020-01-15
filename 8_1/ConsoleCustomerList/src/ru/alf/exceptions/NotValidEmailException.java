package ru.alf.exceptions;

public class NotValidEmailException extends RuntimeException {

    public NotValidEmailException(String message){
        super(message);
    }
}

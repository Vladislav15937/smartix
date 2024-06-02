package ru.my.spring.boot_security.demo.utils.exception;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }
}

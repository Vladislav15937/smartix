package ru.my.spring.boot_security.demo.constant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Constant {

    USER_NOT_EXISTS("Юзер не существует"),

    INSUFFICIENT_FUNDS("Недостаточно средств");

    private final String message;

    Constant(String message) {
        this.message = message;
    }
}

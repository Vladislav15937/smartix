package ru.my.spring.boot_security.demo.dto.mapper;

import ru.my.spring.boot_security.demo.dto.RegistryDto;
import ru.my.spring.boot_security.demo.entity.User;

import java.util.function.Function;

public class UserDtoMapper implements Function<RegistryDto, User> {

    @Override
    public User apply(RegistryDto registryDto) {
        return new User(registryDto.getLogin(), registryDto.getPassword(), 1000.0);
    }

    @Override
    public <V> Function<V, User> compose(Function<? super V, ? extends RegistryDto> before) {
        return Function.super.compose(before);
    }

    @Override
    public <V> Function<RegistryDto, V> andThen(Function<? super User, ? extends V> after) {
        return Function.super.andThen(after);
    }
}

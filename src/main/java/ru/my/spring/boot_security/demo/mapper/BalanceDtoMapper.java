package ru.my.spring.boot_security.demo.mapper;

import org.springframework.stereotype.Service;
import ru.my.spring.boot_security.demo.dto.BalanceDto;
import ru.my.spring.boot_security.demo.entity.User;

import java.util.function.Function;

@Service
public class BalanceDtoMapper implements Function<User, BalanceDto> {

    @Override
    public BalanceDto apply(User user) {
        return new BalanceDto(user.getBalance(), user.getMobileBalance(), user.getUsername());
    }

    @Override
    public <V> Function<V, BalanceDto> compose(Function<? super V, ? extends User> before) {
        return Function.super.compose(before);
    }

    @Override
    public <V> Function<User, V> andThen(Function<? super BalanceDto, ? extends V> after) {
        return Function.super.andThen(after);
    }
}

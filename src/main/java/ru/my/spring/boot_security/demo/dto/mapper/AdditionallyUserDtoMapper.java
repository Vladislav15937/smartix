package ru.my.spring.boot_security.demo.dto.mapper;

import org.springframework.stereotype.Service;
import ru.my.spring.boot_security.demo.dto.AdditionallyUserDto;
import ru.my.spring.boot_security.demo.entity.AdditionallyUser;

import java.util.function.Function;

@Service
public class AdditionallyUserDtoMapper implements Function<AdditionallyUserDto, AdditionallyUser> {

    @Override
    public AdditionallyUser apply(AdditionallyUserDto additionallyUserDto) {
        return new AdditionallyUser(additionallyUserDto.getName(),
                additionallyUserDto.getSurname(),
                additionallyUserDto.getEmail(),
                additionallyUserDto.getPatronymic(),
                additionallyUserDto.getGender(),
                additionallyUserDto.getDateOfBirth());
    }

    @Override
    public <V> Function<V, AdditionallyUser> compose(Function<? super V, ? extends AdditionallyUserDto> before) {
        return Function.super.compose(before);
    }

    @Override
    public <V> Function<AdditionallyUserDto, V> andThen(Function<? super AdditionallyUser, ? extends V> after) {
        return Function.super.andThen(after);
    }
}

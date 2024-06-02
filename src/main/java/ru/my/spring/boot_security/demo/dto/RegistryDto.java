package ru.my.spring.boot_security.demo.dto;

import lombok.*;

import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegistryDto {


    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Некорректный номер телефона")
    private String login;

    private String password;
}

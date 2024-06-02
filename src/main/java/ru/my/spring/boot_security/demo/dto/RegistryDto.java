package ru.my.spring.boot_security.demo.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegistryDto {

    private String login;

    private String password;
}

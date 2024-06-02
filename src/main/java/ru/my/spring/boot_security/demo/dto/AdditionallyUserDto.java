package ru.my.spring.boot_security.demo.dto;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AdditionallyUserDto {

    private String name;

    private String surname;

    private String patronymic;

    private String email;

    private Boolean gender;

    private Date dateOfBirth;
}

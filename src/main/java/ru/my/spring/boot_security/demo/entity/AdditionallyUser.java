package ru.my.spring.boot_security.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "AdditionallyUser")
public class AdditionallyUser {

    public AdditionallyUser(String name, String surname, String patronymic, String email, Boolean gender, Date dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "dateOfBirth")
    private Date dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}

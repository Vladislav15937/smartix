package ru.my.spring.boot_security.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User {

    public User(String username, String password, Double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "login", unique = true)
    private String username;

    @NotNull
    @Column(name = "password", unique = true)
    private String password;

    @NotNull
    @Column(name = "balance")
    private Double balance;

    @Column(name = "mobileBalance")
    private Double mobileBalance;

    @OneToOne(cascade = CascadeType.ALL)
    private AdditionallyUser additionallyUser;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Payments> payments;
}

package ru.my.spring.boot_security.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

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

    @OneToOne(cascade = CascadeType.ALL)
    private AdditionallyUser additionallyUser;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Payments> payments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, Double balance) {
        this.username = username;
        this.balance = balance;
    }

    public User(String username, String password, Double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public User() {
    }

    public User(String username, String password, Double balance, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.roles = roles;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    public String getPassword() {
        return this.password;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Payments> getPayments() {
        return payments;
    }

    public void setPayments(List<Payments> payments) {
        this.payments = payments;
    }

    public AdditionallyUser getAdditionallyUser() {
        return additionallyUser;
    }

    public void setAdditionallyUser(AdditionallyUser additionallyUser) {
        this.additionallyUser = additionallyUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + balance + '\'' +
                ", roles=" + roles +
                '}';
    }
}

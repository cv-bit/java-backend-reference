package com.dpi.publishingapi.data.user;


import com.dpi.publishingapi.data.book.book.Book;
import com.dpi.publishingapi.data.payment.purchase.Purchase;
import com.dpi.publishingapi.security.role.Role;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Nullable
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "verification_code", length = 6)
    private Long verificationCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_method")
    private LoginMethod loginMethod;

    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_purchases", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "purchase_id"))
    private final Set<Purchase> purchases = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_library", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private final Set<Book> library = new HashSet<>();

    public User() {
    }

    public User(String email, String password, Set<Role> roles, Long verificationCode, boolean enabled, LoginMethod loginMethod) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.verificationCode = verificationCode;
        this.enabled = enabled;
        this.loginMethod = loginMethod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Long verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LoginMethod getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(LoginMethod loginMethod) {
        this.loginMethod = loginMethod;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public Set<Book> getLibrary() {
        return library;
    }
}

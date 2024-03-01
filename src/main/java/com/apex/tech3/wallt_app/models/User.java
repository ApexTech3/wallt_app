package com.apex.tech3.wallt_app.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "users", schema = "wallt_db")
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
//    @SequenceGenerator(name = "user_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "user_id")
    private int id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "photo")
    private String profilePicture;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", schema = "wallt_db",
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @Column(name = "blocked")
    private boolean isBlocked;
    @Column(name = "verified")
    private boolean isVerified;
    @Column(name = "confirmation_token")
    private String confirmationToken;
    @CreationTimestamp
    @Column(name = "stamp_created", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp stampCreated;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}

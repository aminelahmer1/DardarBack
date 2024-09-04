package com.example.dardar.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Component
@ToString
@Getter
@Setter
@Data
@Table(name = "User")
public class User {
    @JsonBackReference
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String paswd ;
    private String firstname;
    private String lastname;
    private String SecretKey;
    private String DateOfBirth;
    private String Gender;
    private String email;
    private int PhoneNumber;

    private String resetPasswordToken;
    private boolean banned;
    private Date banExpirationDate;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Search> searches;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Subscribe> subscribes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RDV> rdvs;






}

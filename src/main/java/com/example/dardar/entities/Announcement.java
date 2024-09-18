package com.example.dardar.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Component
@ToString
@Getter
@Setter
@Table(name = "Announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int Id_announcement;

    private String title;
    private String description;
    private String Date;
    private String Prix;
    private  String type_Announcement;
    private String CodePostal;
    private String Ville;
    private String Adresse;
    private String gouvernorat;
    private  String phoneNumber;
    private String imagePath;

    private String email;


}

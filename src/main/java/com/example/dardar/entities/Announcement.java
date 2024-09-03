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
    private  int Id_announcement;

    private String title;
    private String description;
    private String Date;

    @Enumerated(EnumType.STRING)
    private Type_Announcement typeAnnouncement;

   

}

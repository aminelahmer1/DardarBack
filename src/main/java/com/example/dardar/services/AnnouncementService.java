package com.example.dardar.services;


import com.example.dardar.entities.Announcement;
import com.example.dardar.entities.AnnouncementByGovernorate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AnnouncementService {

    public Announcement addAnnouncement(Announcement announcement);

    List<Announcement> getAllAnnouncement();
    public Announcement updateAnnouncement(Integer Id_announcement, Announcement announcement);
    void deleteAnnouncement(Integer Id_announcement);
    Announcement getAnnouncementById(Integer Id_announcement);
    public String uploadImage(Integer Id_announcement, MultipartFile file) ;
    public List<Announcement> searchAnnouncements(String keyword);

    public List<Announcement> filterAnnouncements(String title, String minPrix, String maxPrix, String type_Announcement, String Date, String gouvernorat, String Ville, String Adresse) ;
    public List<AnnouncementByGovernorate> getAnnouncementsByGovernorate() ;



}

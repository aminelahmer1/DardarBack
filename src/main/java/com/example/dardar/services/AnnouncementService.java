package com.example.dardar.services;

import com.example.dardar.entities.Announcement;

import java.util.List;

public interface AnnouncementService {

    public Announcement addAnnouncement(Announcement announcement);

    List<Announcement> getAllAnnouncement();
    public Announcement updateAnnouncement(Integer Id_announcement, Announcement announcement);
    void deleteAnnouncement(Integer Id_announcement);
    Announcement getAnnouncementById(Integer Id_announcement);
}

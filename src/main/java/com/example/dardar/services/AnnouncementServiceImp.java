package com.example.dardar.services;

import com.example.dardar.entities.Announcement;
import com.example.dardar.repositories.AnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AnnouncementServiceImp implements  AnnouncementService{

    @Autowired
    AnnouncementRepository announcementRepository ;

    @Override
    public Announcement addAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
        return announcement;
    }

    @Override
    public List<Announcement> getAllAnnouncement() {
       return announcementRepository.findAll();
    }

    @Override
    public Announcement updateAnnouncement(Integer Id_announcement, Announcement announcement) {
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(Id_announcement);

        if (optionalAnnouncement.isPresent()) {
            Announcement existingAnnouncement = optionalAnnouncement.get();
            existingAnnouncement.setTitle(announcement.getTitle());
            existingAnnouncement.setDescription(announcement.getDescription());

            existingAnnouncement.setDate(announcement.getDate());
            existingAnnouncement.setTypeAnnouncement(announcement.getTypeAnnouncement());


            return announcementRepository.save(existingAnnouncement);
        } else {
            // If no staff is found with the given ID, return null
            return null;
        }
    }

        @Override
    public void deleteAnnouncement(Integer Id_announcement) {
announcementRepository.deleteById(Id_announcement);
    }

    @Override
    public Announcement getAnnouncementById(Integer Id_announcement) {
announcementRepository.findById(Id_announcement).orElse(null);
        return null;
    }
}

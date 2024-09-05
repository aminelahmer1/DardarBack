package com.example.dardar.services;

import com.example.dardar.entities.Announcement;
import com.example.dardar.repositories.AnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnnouncementServiceImp implements AnnouncementService {

    @Autowired
    private final AnnouncementRepository announcementRepository;

    @Override
    public Announcement addAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
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
            existingAnnouncement.setPrix(announcement.getPrix());
            existingAnnouncement.setType_Announcement(announcement.getType_Announcement());
            existingAnnouncement.setCodePostal(announcement.getCodePostal());
            existingAnnouncement.setVille(announcement.getVille());
            existingAnnouncement.setAdresse(announcement.getAdresse());
            existingAnnouncement.setGouvernorat(announcement.getGouvernorat());
            existingAnnouncement.setImagePath(announcement.getImagePath());
            existingAnnouncement.setPhoneNumber(announcement.getPhoneNumber());
            return announcementRepository.save(existingAnnouncement);
        } else {
            return null;
        }
    }

    @Override
    public void deleteAnnouncement(Integer Id_announcement) {
        announcementRepository.deleteById(Id_announcement);
    }

    @Override
    public Announcement getAnnouncementById(Integer Id_announcement) {
        return announcementRepository.findById(Id_announcement).orElse(null);
    }
    @Override
    public String uploadImage(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uploadDir = "announcement-images/";

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Retourner l'URL complète
            return "/announcement-images/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }



}

package com.example.dardar.security.services;


import com.example.dardar.entities.Announcement;
import com.example.dardar.entities.AnnouncementByGovernorate;
import com.example.dardar.repositories.AnnouncementRepository;
import com.example.dardar.services.AnnouncementService;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
            existingAnnouncement.setEmail((announcement.getEmail()));
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
    public String uploadImage(Integer Id_announcement, MultipartFile file) {
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

            // Mettre à jour l'annonce avec le chemin de l'image
            Announcement announcement = announcementRepository.findById(Id_announcement)
                    .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
            announcement.setImagePath("/announcement-images/" + fileName);
            announcementRepository.save(announcement);

            // Retourner l'URL complète
            return "/announcement-images/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Impossible de stocker le fichier. Erreur : " + e.getMessage());
        }
    }
    @Override
    public List<Announcement> searchAnnouncements(String keyword) {
        return announcementRepository.searchAnnouncements(keyword);
    }

    @Override
    public List<Announcement> filterAnnouncements(String title, String minPrix, String maxPrix, String type_Announcement, String Date, String gouvernorat, String Ville, String Adresse) {
        return announcementRepository.filterAnnouncements(title, minPrix, maxPrix, type_Announcement, Date, gouvernorat, Ville, Adresse);
    }
    @Override

    public List<AnnouncementByGovernorate> getAnnouncementsByGovernorate() {
        List<Announcement> announcements = announcementRepository.findAll();

        // Group by governorate and count occurrences
        Map<String, Long> countByGovernorate = announcements.stream()
                .collect(Collectors.groupingBy(Announcement::getGouvernorat, Collectors.counting()));

        return countByGovernorate.entrySet().stream()
                .map(entry -> new AnnouncementByGovernorate(entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());
    }

}

package com.example.darwithoutsecurity.controllers;

import com.example.dardar.entities.Announcement;
import com.example.dardar.entities.AnnouncementByGovernorate;
import com.example.dardar.entities.SearchHistory;
import com.example.dardar.services.AnnouncementService;
import com.example.dardar.services.SearchHistoryService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")

public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final SearchHistoryService searchHistoryService;





    @PostMapping("/addAnnouncement")
    public Announcement addAnnouncement(@RequestBody Announcement announcement) {
        return announcementService.addAnnouncement(announcement);
    }

    @GetMapping("/getAllAnnouncements")
    public List<Announcement> getAllAnnouncements() {
        return announcementService.getAllAnnouncement();
    }

    @PutMapping("/updateAnnouncement/{Id_announcement}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Integer Id_announcement, @RequestBody Announcement announcement) {
        Announcement updatedAnnouncement = announcementService.updateAnnouncement(Id_announcement, announcement);
        if (updatedAnnouncement != null) {
            return ResponseEntity.ok(updatedAnnouncement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteAnnouncement/{Id_announcement}")
    public void deleteAnnouncement(@PathVariable Integer Id_announcement) {
        announcementService.deleteAnnouncement(Id_announcement);
    }

    @GetMapping("/getAnnouncementById/{Id_announcement}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Integer Id_announcement) {
        Announcement announcement = announcementService.getAnnouncementById(Id_announcement);
        if (announcement != null) {
            return ResponseEntity.ok(announcement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/uploadImage/{Id_announcement}")
    public ResponseEntity<String> uploadImage(
            @PathVariable("Id_announcement") Integer Id_announcement,
            @RequestParam("file") MultipartFile file) {
        String filePath = announcementService.uploadImage(Id_announcement, file);
        return new ResponseEntity<>(filePath, HttpStatus.OK);
    }

    @GetMapping("/getImage/{Id_announcement}")
    public ResponseEntity<byte[]> getImage(@PathVariable("Id_announcement") Integer Id_announcement) {
        try {
            // Récupérer l'annonce par son Id
            Announcement announcement = announcementService.getAnnouncementById(Id_announcement);

            // Chemin de l'image
            String imagePath = announcement.getImagePath();
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            String contentType = Files.probeContentType(path);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/search")
    public List<Announcement> searchAnnouncements(@RequestParam(required = false) String keyword) {
        searchHistoryService.saveSearchHistory(keyword);

        return announcementService.searchAnnouncements(keyword);
    }



    @GetMapping("/filterAnnouncements")
    public List<Announcement> filterAnnouncements(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) String minPrix,
                                                  @RequestParam(required = false) String maxPrix,
                                                  @RequestParam(required = false) String type_Announcement,
                                                  @RequestParam(required = false) String Date,
                                                  @RequestParam(required = false) String gouvernorat,
                                                  @RequestParam(required = false) String Ville,
                                                  @RequestParam(required = false) String Adresse) {
        return announcementService.filterAnnouncements(title, minPrix, maxPrix, type_Announcement, Date, gouvernorat, Ville, Adresse);
    }
    @GetMapping("/searchHistory")
    public List<SearchHistory> getSearchHistory() {
        return searchHistoryService.getAllSearchHistory();
    }


    @GetMapping("/gouvernorat")
    public List<AnnouncementByGovernorate> getAnnouncementsByGovernorate() {
        return announcementService.getAnnouncementsByGovernorate();
    }


    @GetMapping("/announcement-images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("C:/Users/MSI/OneDrive/Bureau/darwithoutsecurity/announcement-images").resolve(filename).normalize();
            Resource resource = (Resource) new UrlResource(imagePath.toUri());

            // Vérifiez si le fichier est un fichier régulier et accessible
            if (((UrlResource) resource).isFile()) {
                InputStream inputStream = ((UrlResource) resource).getInputStream();
                return ResponseEntity.ok()
                        .contentType(getMediaTypeForFileName(filename))
                        .body((Resource) new InputStreamResource(inputStream));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Méthode pour obtenir le type de contenu en fonction du nom du fichier
    private MediaType getMediaTypeForFileName(String filename) {
        if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (filename.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM; // Par défaut pour les types inconnus
        }
    }


}
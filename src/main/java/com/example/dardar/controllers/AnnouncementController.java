package com.example.dardar.controllers;

import com.example.dardar.entities.Announcement;
import com.example.dardar.services.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")

public class AnnouncementController {

    private final AnnouncementService announcementService;

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

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = announcementService.uploadImage(file);
        return ResponseEntity.ok("Image uploaded successfully: " + fileName);
    }
}

package com.example.dardar.security.services;

import com.example.dardar.entities.User;
import com.example.dardar.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    public static final String uploadDirectory = "C:/xampp/htdocs/images/";

    public User update(User user, MultipartFile imageFile) {
        try {
            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            String originalFilename = imageFile.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

            Path filePath = Paths.get(uploadDirectory, fileName);

            Files.write(filePath, imageFile.getBytes());


            return userRepository.save(user);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image");
        }
    }
}

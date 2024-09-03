package com.example.dardar.repositories;

import com.example.dardar.entities.Announcement;
import com.example.dardar.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}

package com.example.dardar.repositories;


import com.example.dardar.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    @Query("SELECT a FROM Announcement a WHERE " +
            "(:keyword is null or a.title like %:keyword% or a.description like %:keyword% or a.Date like %:keyword% or " +
            "a.Prix like %:keyword% or a.type_Announcement like %:keyword% or a.CodePostal like %:keyword% or " +
            "a.Ville like %:keyword% or a.Adresse like %:keyword% or a.gouvernorat like %:keyword% or " +
            "a.phoneNumber like %:keyword% or a.email like %:keyword%)")
    List<Announcement> searchAnnouncements(@Param("keyword") String keyword);


    @Query("SELECT a FROM Announcement a WHERE " +
            "(:title is null or :title = '' or a.title like %:title%) and " +
            "(:minPrix is null or :minPrix = '' or a.Prix >= :minPrix) and " +
            "(:maxPrix is null or :maxPrix = '' or a.Prix <= :maxPrix) and " +
            "(:type_Announcement is null or :type_Announcement = '' or a.type_Announcement = :type_Announcement) and " +
            "(:Date is null or :Date = '' or a.Date like %:Date%) and " +
            "(:gouvernorat is null or :gouvernorat = '' or a.gouvernorat like %:gouvernorat%) and " +
            "(:Ville is null or :Ville = '' or a.Ville like %:Ville%) and " +
            "(:Adresse is null or :Adresse = '' or a.Adresse like %:Adresse%)")
    List<Announcement> filterAnnouncements(@Param("title") String title,
                                           @Param("minPrix") String minPrix,
                                           @Param("maxPrix") String maxPrix,
                                           @Param("type_Announcement") String type_Announcement,
                                           @Param("Date") String Date,
                                           @Param("gouvernorat") String gouvernorat,
                                           @Param("Ville") String Ville,
                                           @Param("Adresse") String Adresse);

    @Query("SELECT a.gouvernorat, COUNT(a) FROM Announcement a GROUP BY a.gouvernorat")
    Map<String, Long> countByGouvernorat();
}

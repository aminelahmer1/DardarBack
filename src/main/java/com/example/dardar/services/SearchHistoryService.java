package com.example.dardar.services;

import com.example.dardar.entities.SearchHistory;
import com.example.dardar.repositories.SearchHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    public void saveSearchHistory(String keyword) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setKeyword(keyword);
        searchHistory.setSearchDate(LocalDateTime.now());
        searchHistoryRepository.save(searchHistory);
    }

    public List<SearchHistory> getAllSearchHistory() {

        return   searchHistoryRepository.findAll();

    }}

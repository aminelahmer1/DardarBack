package com.example.dardar.entities;

public class AnnouncementByGovernorate {
    private final String governorate;
    private final int count;

    public AnnouncementByGovernorate(String governorate, int count) {
        this.governorate = governorate;
        this.count = count;
    }

    public String getGovernorate() {
        return governorate;
    }

    public int getCount() {
        return count;
    }
}

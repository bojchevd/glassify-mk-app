package com.example.glassify.service;

import org.springframework.stereotype.Service;

@Service
public class UtilityService {

    public boolean isNullOrEmpty(String input) {
        if (input == null || input.length() < 1 || input.equalsIgnoreCase("null")) {
            return true;
        } else return false;
    }
}

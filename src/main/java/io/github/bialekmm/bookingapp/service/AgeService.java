package io.github.bialekmm.bookingapp.service;


import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;


public interface AgeService {

    DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDateTime now = LocalDateTime.now();
    int ageFromBirthDate(String email, String birthDate);
}

package io.github.bialekmm.bookingapp.service;

import java.time.format.DateTimeFormatter;

public interface AgeService {

    DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    int ageFromBirthDate(String email, String birthDate);
}

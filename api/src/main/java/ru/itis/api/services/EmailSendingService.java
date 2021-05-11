package ru.itis.api.services;

public interface EmailSendingService {

    void sendEmail(String to, String letter, String subject);

}

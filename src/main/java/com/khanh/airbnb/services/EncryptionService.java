package com.khanh.airbnb.services;

public interface EncryptionService {
    String encryptPassword(String password);
    boolean verifyPassword(String password, String hash);
}

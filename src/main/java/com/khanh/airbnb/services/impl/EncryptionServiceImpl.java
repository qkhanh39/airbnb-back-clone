package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.services.EncryptionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {
    @Value("${encryption.salt.rounds}")
    private int saltRounds;

    private String salt;

    @PostConstruct
    public void postConstruct() {
        salt = BCrypt.gensalt(saltRounds);
    }

    void setSaltRoundsForTest(int saltRounds) {
        this.saltRounds = saltRounds;
    }

    @Override
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}

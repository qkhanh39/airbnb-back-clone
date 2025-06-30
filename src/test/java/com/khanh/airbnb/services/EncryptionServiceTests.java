package com.khanh.airbnb.services;

import com.khanh.airbnb.services.impl.EncryptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EncryptionServiceTests {

    private EncryptionServiceImpl encryptionService;

    @BeforeEach
    void setUp() {
        encryptionService = new EncryptionServiceImpl();
        ReflectionTestUtils.setField(encryptionService, "saltRounds", 10);
        encryptionService.postConstruct();
    }

    @Test
    void testVerifyPasswordReturnsTrueForCorrectPassword() {
        String rawPassword = "123456";
        String hashedPassword = encryptionService.encryptPassword(rawPassword);

        boolean result = encryptionService.verifyPassword(rawPassword, hashedPassword);
        assertTrue(result);
    }

    @Test
    void testVerifyPassWordReturnsFalseForIncorrectPassword() {
        String correctPassword = "123456";
        String wrongPassword = "654321";

        String hashedWrongPassword = encryptionService.encryptPassword(wrongPassword);
        boolean result = encryptionService.verifyPassword(correctPassword, hashedWrongPassword);
        assertFalse(result);
    }
}

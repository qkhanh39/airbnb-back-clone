package com.khanh.airbnb.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.exceptions.AuthenticationErrorException;
import com.khanh.airbnb.services.impl.JWTServiceImpl;
import com.khanh.airbnb.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JWTServiceTests {
    private JWTServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JWTServiceImpl();
        ReflectionTestUtils.setField(jwtService, "algorithmKey", "test-secret-key");
        ReflectionTestUtils.setField(jwtService, "issuer", "test-issuer");
        ReflectionTestUtils.setField(jwtService, "expiryInSeconds", 604800);
        jwtService.postConstruct();
    }

    @Test
    void testJWTServiceCreatesValidToken() {
        UserEntity testUser = TestDataUtil.createTestUser1();
        String generatedToken = jwtService.generateJWT(testUser);

        assertNotNull(generatedToken);
        assertDoesNotThrow(() -> jwtService.validateToken(generatedToken));
        assertEquals(testUser.getUsername(), jwtService.getUsername(generatedToken));
    }

    @Test
    void testJWTServiceThrowsExceptionWhenInvalidToken() {
        String invalidToken = "invalid-token";

        assertThrows(AuthenticationErrorException.class, () -> jwtService.validateToken(invalidToken));
    }

    @Test
    void testJWTServiceReturnsNullWhenMissingClaim() {
        String token = JWT.create()
                .withIssuer("test-issuer")
                .sign(Algorithm.HMAC256("test-secret-key"));
        assertNull(jwtService.getUsername(token));
    }
}

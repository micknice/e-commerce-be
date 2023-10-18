package com.ecommerce_be.ecommerce_backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.ecommerce_be.ecommerce_backend.exception.EmailNotFoundException;
import com.ecommerce_be.ecommerce_backend.model.LocalUser;
import com.ecommerce_be.ecommerce_backend.model.dao.LocalUserDAO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Class to test the JWTService.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private LocalUserDAO localUserDAO;

    @Autowired
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;


    @Test

    public void testVerificationTokenNotUsableForLogin() {
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateVerificationJWT(user);
        Assertions.assertNull(jwtService.getUsername(token), "Verification token should not contain username.");
    }

    @Test
    public void testAuthTokenReturnsUsername() {
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(user);
        Assertions.assertEquals(user.getUsername(), jwtService.getUsername(token), "Token for auth should contain users username.");
    }

    @Test
    public void testLoginJWTNotGeneratedByUs() {
        String token = JWT.create().withClaim("USERNAME", "UserA")
                    .sign(Algorithm.HMAC256("InvalidSecret"));
        Assertions.assertThrows(SignatureVerificationException.class,
                () -> jwtService.getUsername(token));
    }

    @Test
    public void testLoginJWTCorrectlySignedNoIssuer() {
        String token = JWT.create().withClaim("USERNAME", "UserA")
                .sign(Algorithm.HMAC256(algorithmKey));
        Assertions.assertThrows(MissingClaimException.class,
                () -> jwtService.getUsername(token));
    }

    @Test
    public void testPasswordResetToken() {
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generatePasswordResetJWT(user);
        Assertions.assertEquals(user.getEmail(),
                jwtService.getResetPasswordEmail(token), "Email should match inside " +
                        "JWT.");
    }

    @Test
    public void testPasswordResetJWTNotGeneratedByUs() {
        String token = JWT.create().withClaim("RESET_PASSWORD_EMAIL", "UserA@junit.com")
                .sign(Algorithm.HMAC256("InvalidSecret"));
        Assertions.assertThrows(SignatureVerificationException.class,
                () -> jwtService.getResetPasswordEmail(token));
    }

    @Test
    public void testPasswordResetJWTCorrectlySignedNoIssuer() {
        String token = JWT.create().withClaim("RESET_PASSWORD_EMAIL", "UserA@junit.com")
                .sign(Algorithm.HMAC256(algorithmKey));
        Assertions.assertThrows(MissingClaimException.class,
                () -> jwtService.getResetPasswordEmail(token));
    }



}

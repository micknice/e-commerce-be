package com.ecommerce_be.ecommerce_backend.service;

import com.ecommerce_be.ecommerce_backend.model.LocalUser;
import com.ecommerce_be.ecommerce_backend.model.dao.LocalUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Class to test the JWTService.
 */
@SpringBootTest
@ActiveProfiles("test")
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private LocalUserDAO localUserDAO;


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

}

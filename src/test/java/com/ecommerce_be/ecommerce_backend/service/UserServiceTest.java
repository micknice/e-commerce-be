package com.ecommerce_be.ecommerce_backend.service;

import com.ecommerce_be.ecommerce_backend.api.model.LoginBody;
import com.ecommerce_be.ecommerce_backend.api.model.RegistrationBody;
import com.ecommerce_be.ecommerce_backend.exception.EmailFailureException;
import com.ecommerce_be.ecommerce_backend.exception.UserAlreadyExistsException;
import com.ecommerce_be.ecommerce_backend.exception.UserNotVerifiedException;
import com.ecommerce_be.ecommerce_backend.model.VerificationToken;
import com.ecommerce_be.ecommerce_backend.model.dao.VerificationTokenDAO;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret"))
            .withPerMethodLifecycle(true);
    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenDAO verificationTokenDAO;


    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
//        create registration body
        RegistrationBody body = new RegistrationBody();
        body.setUsername("UserA");
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("MySecretPassword123");
//        verify rejection of registration w/ existing username
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Username should already be in use.");
//        set unique user but existing email
        body.setUsername("UserServiceTest$testRegisterUser");
        body.setEmail("UserA@junit.com");
//        verify rejection of registration w/ existing email
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Email should already be in use.");
//        set unique username and email
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
//        verify registration accepted w/ unique username and email
        Assertions.assertDoesNotThrow(() -> userService.registerUser(body),
                "User should register successfully.");
        Assertions.assertEquals(body.getEmail(), greenMailExtension.getReceivedMessages()[0]
                .getRecipients(Message.RecipientType.TO)[0].toString());
    }

    @Test
    @Transactional
    public void testLoginUser() throws UserNotVerifiedException, EmailFailureException {
//        create login body
        LoginBody body = new LoginBody();
        body.setUsername("UserA-NotExists");
        body.setPassword("PasswordA123-BadPassword");
//        verify rejection of login w/ invalid username
        Assertions.assertNull(userService.loginUser(body), "User should not exist");
//        set valid username
        body.setUsername("UserA");
//        verify rejection of login w/ invalid password
        Assertions.assertNull(userService.loginUser(body), "Password should be incorrect");
//        set valid password
        body.setPassword("PasswordA123");
//        verify successful login w/ valid username and password
        Assertions.assertNotNull(userService.loginUser(body), "User should log in successfully");
//        set username and password to valid but unverified user
        body.setUsername("UserB");
        body.setPassword("PasswordB123");
        try {
//            verify email not verified
            userService.loginUser(body);
            Assertions.assertTrue(false, "User should not be email verified");
        } catch (UserNotVerifiedException ex){
//            verify email verification has been sent
            Assertions.assertTrue(ex.isNewEmailSent(), "Email verification should be sent");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }try {
//            verify email not verified for second time
            userService.loginUser(body);
            Assertions.assertTrue(false, "User should not be email verified");
        } catch (UserNotVerifiedException ex){
//            verify that second verification email is not sent
            Assertions.assertFalse(ex.isNewEmailSent(), "Email verification should not be re-sent");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }
    }

    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException {
        Assertions.assertFalse(userService.verifyUser("Bad Token"), "Token that is bad or does not exist should return false.");
        LoginBody body = new LoginBody();
        body.setUsername("UserB");
        body.setPassword("PasswordB123");
        try {
            userService.loginUser(body);
            Assertions.assertTrue(false, "User should not be email verified.");
        } catch (UserNotVerifiedException ex) {
            List<VerificationToken> tokens = verificationTokenDAO.findByUser_IdOrderByIdDesc(2L);
            String token = tokens.get(0).getToken();
            Assertions.assertTrue(userService.verifyUser(token), "Token should be valid.");
            Assertions.assertNotNull(body, "The user should be verified.");
        }
    }

}

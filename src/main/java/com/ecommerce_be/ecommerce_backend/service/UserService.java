package com.ecommerce_be.ecommerce_backend.service;

import com.ecommerce_be.ecommerce_backend.api.model.LoginBody;
import com.ecommerce_be.ecommerce_backend.api.model.LoginResponse;
import com.ecommerce_be.ecommerce_backend.api.model.PasswordResetBody;
import com.ecommerce_be.ecommerce_backend.api.model.RegistrationBody;
import com.ecommerce_be.ecommerce_backend.exception.EmailFailureException;
import com.ecommerce_be.ecommerce_backend.exception.EmailNotFoundException;
import com.ecommerce_be.ecommerce_backend.exception.UserNotVerifiedException;
import com.ecommerce_be.ecommerce_backend.model.VerificationToken;
import com.ecommerce_be.ecommerce_backend.model.dao.LocalUserDAO;
import com.ecommerce_be.ecommerce_backend.exception.UserAlreadyExistsException;
import com.ecommerce_be.ecommerce_backend.model.LocalUser;
import com.ecommerce_be.ecommerce_backend.model.dao.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final LocalUserDAO localUserDAO;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    private EmailService emailService;
    private VerificationTokenDAO verificationTokenDAO;

    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService, EmailService emailService, VerificationTokenDAO verificationTokenDAO) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.verificationTokenDAO = verificationTokenDAO;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
        || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        return localUserDAO.save(user);
    }

    private VerificationToken createVerificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        //the below line has been inserted to fix the apparent non write of v tokens on verification may break things
//        verificationTokenDAO.save(verificationToken);
        return verificationToken;
    }
    @Transactional
    public LoginResponse loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    String jwtToken = jwtService.generateJWT(user);
                    LoginResponse logRes = new LoginResponse();
                    logRes.setJwt(jwtToken);
                    logRes.setUser(user);


                    return  logRes;
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 ||
                            verificationTokens.get(0).getCreatedTimeStamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000 )));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }

            }
        }
        return null;
    }
    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if (opToken.isPresent()) {
           VerificationToken verificationToken = opToken.get();
           LocalUser user = verificationToken.getUser();
           if (!user.isEmailVerified()) {
               user.setEmailVerified(true);
               localUserDAO.save(user);
               verificationTokenDAO.deleteByUser(user);
               return true;
           }
        }
        return false;
    }

    public void forgotPassword(String email) throws EmailNotFoundException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            String token = jwtService.generatePasswordResetJWT(user);
            emailService.sendPasswordResetEmail(user, token);
        } else {
            throw new EmailNotFoundException();
        }
    }

    public void resetPassword(PasswordResetBody body) {
        String email = jwtService.getResetPasswordEmail(body.getToken());
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            user.setPassword(encryptionService.encryptPassword(body.getPassword()));
            localUserDAO.save(user);
        }
    }

    public boolean userHasPermissionToUser(LocalUser user, Long id) {
//        TODO: below statement returns true if both null is this a problem? possibly. investigate.
        Long userId = user.getId();

        if(Objects.equals(userId, id)) {
            return true;
        } else {
            return false;
        }
//        return user.getId() == id;
    }
}

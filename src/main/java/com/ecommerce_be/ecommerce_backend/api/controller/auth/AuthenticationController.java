package com.ecommerce_be.ecommerce_backend.api.controller.auth;

import com.ecommerce_be.ecommerce_backend.api.model.LoginBody;
import com.ecommerce_be.ecommerce_backend.api.model.LoginResponse;
import com.ecommerce_be.ecommerce_backend.api.model.PasswordResetBody;
import com.ecommerce_be.ecommerce_backend.api.model.RegistrationBody;
import com.ecommerce_be.ecommerce_backend.exception.EmailFailureException;
import com.ecommerce_be.ecommerce_backend.exception.EmailNotFoundException;
import com.ecommerce_be.ecommerce_backend.exception.UserAlreadyExistsException;
import com.ecommerce_be.ecommerce_backend.exception.UserNotVerifiedException;
import com.ecommerce_be.ecommerce_backend.model.LocalUser;
import com.ecommerce_be.ecommerce_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins="*")
    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            System.out.println(registrationBody);
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EmailFailureException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @CrossOrigin(origins="*")
    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = null;
        LocalUser authUser = null;
        try {
//            jwt = userService.loginUser(loginBody);
            LoginResponse logRes = userService.loginUser(loginBody);
            jwt = logRes.getJwt();
            authUser = logRes.getUser();
        } catch (UserNotVerifiedException ex) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            String reason = "USER_NOT_VERIFIED";
            if (ex.isNewEmailSent()) {
                reason += "_EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (EmailFailureException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            response.setUser(authUser);
            return ResponseEntity.ok(response);
        }
    }
    @CrossOrigin(origins="*")
    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        return user;
    }
    @CrossOrigin(origins="*")
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @CrossOrigin(origins="*")
    @PostMapping("/forgot")
    public ResponseEntity forgotPassword(@RequestParam String email) {
        try {
            userService.forgotPassword(email);
            return ResponseEntity.ok().build();
        } catch (EmailNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EmailFailureException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @CrossOrigin(origins="*")
    @PostMapping("/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody PasswordResetBody body) {
        userService.resetPassword(body);
        return ResponseEntity.ok().build();
    }
}

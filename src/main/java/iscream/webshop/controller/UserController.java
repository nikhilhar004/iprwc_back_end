package iscream.webshop.controller;

import iscream.webshop.model.JWTPayload;
import iscream.webshop.model.User;
import iscream.webshop.record.ChangePassword;
import iscream.webshop.record.LoginRequest;
import iscream.webshop.record.RegisterRequest;
import iscream.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class UserController {
    private static final String SERVER_GOT_ERROR = "Er is iets fout gegaan op de server, probeer het later opnieuw";
    private static final String NEW_USER_MADE = "Nieuwe gebruiker aangemaakt";
    private static final String USER_ALREADY_EXISTS = "gebuiker bestaat al";
    private static final String INVALID_PASSWORD = "Ongeldige inloggegevens";
    private static final String CHANGED_PASSWORD_SUCCESS = "Uw nieuwe wachtwoord is opgeslagen";
    private static final String CHANGED_PASSWORD_FALIED = "Er is iets fout gegaan bij het updaten van Uw nieuwe wachtwoord, probeer het later opnieuw";
    private static final String PASSWORD_DOES_NOT_MATCH = "Wachtwoord komt niet overeen";
    @Autowired
    private UserService userService;
    @Autowired private AuthenticationManager authManager;

    @PostMapping("/register")
    public JWTPayload registerHandler(@RequestBody RegisterRequest registerRequest){
        try {
            String token = userService.register(registerRequest);
            if (token.contains(USER_ALREADY_EXISTS)) return new JWTPayload("", USER_ALREADY_EXISTS, false);
            return new JWTPayload(token, NEW_USER_MADE, true);
        } catch (AuthenticationException e) {
            return new JWTPayload("", SERVER_GOT_ERROR, false);
        }
    }

    @PostMapping("/login")
    public JWTPayload loginHandler(@RequestBody LoginRequest body){
        try {
            String token = userService.login(body, authManager);
            return new JWTPayload(token, "", true);
        }catch (AuthenticationException authExc){
            return new JWTPayload("", INVALID_PASSWORD, false);
        }
    }

    @PostMapping("/changepassword")
    public JWTPayload changePassword(@RequestBody ChangePassword body){
        try {
            String token = userService.changePassword(body, authManager);
            if (token.equals(PASSWORD_DOES_NOT_MATCH)) {
                return new JWTPayload("", PASSWORD_DOES_NOT_MATCH, false);
            }
            return new JWTPayload(token, CHANGED_PASSWORD_SUCCESS, true);
        }catch (AuthenticationException authExc){
            return new JWTPayload("", PASSWORD_DOES_NOT_MATCH, false);
        }
    }


    @GetMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserByID(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        };
        return null;
    }

    public User getAuthUser(Authentication authentication) {
        return userService.getAuthUser(authentication.getName());
    }
}

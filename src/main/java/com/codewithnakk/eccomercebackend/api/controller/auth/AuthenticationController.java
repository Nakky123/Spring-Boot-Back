package com.codewithnakk.eccomercebackend.api.controller.auth;

import com.codewithnakk.eccomercebackend.api.model.LoginBody;
import com.codewithnakk.eccomercebackend.api.model.LoginResponse;
import com.codewithnakk.eccomercebackend.api.model.RegistrationBody;
import com.codewithnakk.eccomercebackend.exception.UserAlreadyExistException;
import com.codewithnakk.eccomercebackend.model.LocalUser;
import com.codewithnakk.eccomercebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    //
    private UserService userService;
    public AuthenticationController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody){
       try{
           userService.registerUser(registrationBody);
           return ResponseEntity.ok().build();
       }catch(UserAlreadyExistException e){
//           System.out.println(ResponseEntity.status(HttpStatus.CONFLICT).build());
           return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody){
        try{
            String jwt = userService.loginUser(loginBody);
            if (jwt == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setJwt(jwt);
                String email = loginBody.getEmail();
                loginResponse.setEmail(email);
                return ResponseEntity.ok(loginResponse);
            }

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserDataByEmail(@PathVariable String email) {
        // Logic to fetch user data based on the provided email
        // This could involve calling a service method to fetch user information from the database

        // Example: Fetch user by email and return it
        LocalUser user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

package com.codewithnakk.eccomercebackend.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {
    // encryption.salt.rounds it's value is set to 10
    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    private String salt;

    @PostConstruct
    public void postConstruct(){
        // After Bcrpyt the saltRounds salt = $2a$10$Vr1y8ZXSBjEY8bYVlPxWL
        salt = BCrypt.gensalt(saltRounds);
    }

    //Ecrpyt password function
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password , salt);
    }

    //Verify true or false
    public boolean verifyPassword(String password , String hash){
        return BCrypt.checkpw(password , hash);
    }
}

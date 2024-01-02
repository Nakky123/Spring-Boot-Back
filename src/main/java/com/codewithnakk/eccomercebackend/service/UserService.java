package com.codewithnakk.eccomercebackend.service;

import com.codewithnakk.eccomercebackend.api.model.LoginBody;
import com.codewithnakk.eccomercebackend.api.model.RegistrationBody;
import com.codewithnakk.eccomercebackend.exception.UserAlreadyExistException;
import com.codewithnakk.eccomercebackend.model.LocalUser;
import com.codewithnakk.eccomercebackend.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private LocalUserDAO localUserDAO;
    private EncryptionService encryptionService;
    private JWTService jwtService;

    public UserService(LocalUserDAO localUserDAO , EncryptionService encryptionService , JWTService jwtService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistException();
        }

        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());

        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));

        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody loginBody){
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(loginBody.getEmail());
        if (opUser.isPresent()){
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword() , user.getPassword())){
                 return jwtService.generateJWT(user);
            }
        }
        return null;
    }

    public LocalUser getUserByEmail(String email) {
        Optional<LocalUser> user = localUserDAO.findByEmail(email);
        return user.orElse(null);
    }

}


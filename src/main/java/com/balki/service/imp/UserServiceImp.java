package com.balki.service.imp;

import com.balki.config.JwtProvider;
import com.balki.model.User;
import com.balki.repository.UserRepository;
import com.balki.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

     JwtProvider jwtProvider;

     UserRepository userRepository;

    public UserServiceImp(JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return findUserByEmail(email);
    }


    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User not found");
        }

        return user;
    }
}

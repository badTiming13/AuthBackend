package com.example.AuthBackend.appuser;

import com.example.AuthBackend.registration.token.ConfirmationToken;
import com.example.AuthBackend.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepo appUserRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepo.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User with email %s not found", username)));
    }
    public String signupUser(AppUser appUser){
       boolean userExists = appUserRepo.findByEmail(appUser.getEmail())
                .isPresent();
       if (userExists){
           throw new IllegalStateException("Email already taken");
       }
       String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
       appUser.setPassword(encodedPassword);
       appUserRepo.save(appUser);

       String token = UUID.randomUUID().toString();
       ConfirmationToken confToken = new ConfirmationToken(token,LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
       confirmationTokenService.saveConfToken(confToken);
       // TODO: SEND EMAIL
       return token;
    }
}

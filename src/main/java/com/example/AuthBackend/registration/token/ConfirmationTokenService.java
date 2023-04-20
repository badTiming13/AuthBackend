package com.example.AuthBackend.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepo confirmationTokenRepo;
    public void saveConfToken(ConfirmationToken token) {
        confirmationTokenRepo.save(token);
    }


}

package org.example.storemanagementbestpractice.services;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.models.EmailStatusEntity;
import org.example.storemanagementbestpractice.repository.EmailStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserRegistrationEmailTokenService {

    private final EmailStatusRepository emailStatusRepository;

    public UserRegistrationEmailTokenService(EmailStatusRepository emailStatusRepository) {
        this.emailStatusRepository = emailStatusRepository;
    }

    public EmailStatusEntity createOrUpdateToken(UUID userId) {
        String token = UUID.randomUUID().toString();
        EmailStatusEntity emailStatusEntity = new EmailStatusEntity();
        Optional<EmailStatusEntity> emailStatusEntityOptional = emailStatusRepository.findEmailTokenByUserId(userId);
        emailStatusEntity = emailStatusEntityOptional.orElse(new EmailStatusEntity(
                token,
                false,
                userId
        ));

        emailStatusEntity.setEmailToken(token);
        emailStatusRepository.save(emailStatusEntity);
        log.info("registration token created");
        return emailStatusEntity;

    }
}

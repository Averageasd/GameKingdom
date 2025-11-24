package org.example.storemanagementbestpractice.services;

import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.exceptions.EmailTokenNotFoundException;
import org.example.storemanagementbestpractice.exceptions.UserNotFoundException;
import org.example.storemanagementbestpractice.models.EmailStatusEntity;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.repository.EmailStatusRepository;
import org.example.storemanagementbestpractice.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmailService {

    @Value("${mail.token}")
    private String mailServiceToken;
    private final EmailStatusRepository emailStatusRepository;
    private final UserDetailsRepository userDetailsRepository;

    public EmailService(EmailStatusRepository emailStatusRepository, UserDetailsRepository userDetailsRepository) {
        this.emailStatusRepository = emailStatusRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public void sendEmail(String mailTrapToken, String email) {
        String subject = "Verify your account";
        log.debug("email service token {}", mailServiceToken);

        String content = """
                Hi new study app enjoyer,
                <br/>
                Thanks for registering. Please verify your email by using the following token:
                <p>%s</p><br/>
                Cheers,<br>
                StudyApp
                """.formatted(mailTrapToken);

        final MailtrapConfig config = new MailtrapConfig.Builder()
                .token(mailServiceToken)
                .build();

        final MailtrapClient client = MailtrapClientFactory.createMailtrapClient(config);

        final MailtrapMail mail = MailtrapMail.builder()
                .from(new Address("app@studyapp.work", "Mailtrap Test"))
                .to(List.of(new Address(email)))
                .subject(subject)
                .html(content)
                .build();

        try {
            System.out.println(client.send(mail));
            log.info("User registration email sent");
        } catch (Exception e) {
            log.error("Caught exception: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void verifyToken(String token) {
        EmailStatusEntity emailStatusEntity = emailStatusRepository.findByEmailToken(token)
                .orElseThrow(() -> new EmailTokenNotFoundException(EmailTokenNotFoundException.EMAIL_TOKEN_NOT_FOUND));
        UserEntity userEntity = emailStatusRepository.findByUserId(emailStatusEntity.getUserId())
                .orElseThrow(() -> new UserNotFoundException(UserNotFoundException.USER_NOT_FOUND));
        emailStatusRepository.delete(emailStatusEntity);
        userEntity.setEnabled(true);
        userDetailsRepository.save(userEntity);
        log.info("token verified");
    }
}

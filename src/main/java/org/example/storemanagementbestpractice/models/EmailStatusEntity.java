package org.example.storemanagementbestpractice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "studyAppEmailStatus")
public class EmailStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, unique = true)
    private int id;

    @Column(nullable = false, unique = true)
    private String emailToken;

    @Column(nullable = false)
    private boolean emailVerified;

    @Column(nullable = false)
    private UUID userId;

    public EmailStatusEntity(
            String emailToken,
            boolean emailVerified,
            UUID userId
    ) {
        this.emailToken = emailToken;
        this.emailVerified = emailVerified;
        this.userId = userId;
    }
}

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
    private Long id;

    @Column(nullable = false, unique = true)
    private String emailToken;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private UUID userId;
}

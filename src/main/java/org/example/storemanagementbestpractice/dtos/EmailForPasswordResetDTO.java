package org.example.storemanagementbestpractice.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class EmailForPasswordResetDTO {
    @Column(nullable = false, unique = true)
    private final String email;
}

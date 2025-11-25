package org.example.storemanagementbestpractice.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordResetDTO {
    @NotNull
    @Length(min = 5, max = 255, message = "Password length must be between 5 and 255 characters")
    private final String newPassword;
}

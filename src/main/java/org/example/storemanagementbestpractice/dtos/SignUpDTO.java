package org.example.storemanagementbestpractice.dtos;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Data
public class SignUpDTO {

    @NotNull
    @Length(min = 5, max = 255, message = "Name length must be between 5 and 255 characters")
    private final String username;

    @NotNull
    private final String email;

    @NotNull
    @Length(min = 5, max = 255, message = "Password length must be between 5 and 255 characters")
    private final String password;
}

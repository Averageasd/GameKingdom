package org.example.storemanagementbestpractice.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class GameSessionSearchRequestDTO {

    @NotBlank
    @Length(min = 5, max = 255, message = "session name length must be between 5 and 255 characters")
    private String sessionName;

    @NotBlank
    @Column(nullable = false)
    private String gameStatus;

    @NotBlank
    @Column(nullable = false)
    private String gameType;
}

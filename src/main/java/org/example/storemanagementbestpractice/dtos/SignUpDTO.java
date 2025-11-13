package org.example.storemanagementbestpractice.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SignUpDTO {
    private final String username;
    private final String email;
    private final String password;
}

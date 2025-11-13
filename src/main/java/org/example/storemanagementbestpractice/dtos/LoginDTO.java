package org.example.storemanagementbestpractice.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
//@Getter
//@Setter
public class LoginDTO {
    private final String username;
    private final String password;
}

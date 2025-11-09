package org.example.storemanagementbestpractice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginModel {

    private final String username;
    private final String password;
}

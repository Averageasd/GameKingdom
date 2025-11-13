package org.example.storemanagementbestpractice.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "studyAppUser")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false)
    @Length(min = 5, max = 255, message = "Name length must be between 5 and 255 characters")
    private String username;

    @Column(nullable = false, unique = true)
    private boolean enabled;

    @Column(nullable = false)
    @Length(min = 5, max = 255, message = "Password length must be between 5 and 255 characters")
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    public UserEntity(String username, boolean enabled, String password, String email) {
        this.username = username;
        this.enabled = enabled;
        this.password = password;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

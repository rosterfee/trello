package ru.itis.web.security.api.authentications;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.itis.web.security.details.UserDetailsImpl;

import java.util.Collection;

@Data
public class JwtAuthentication implements Authentication {

    private UserDetailsImpl userDetails;
    private boolean isAuthenticated;
    private String email;

    public JwtAuthentication(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        if (userDetails != null) {
            return userDetails.getUserDTO();
        } else return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return email;
    }
}

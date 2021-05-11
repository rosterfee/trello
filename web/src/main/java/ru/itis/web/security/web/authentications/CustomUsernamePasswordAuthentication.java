package ru.itis.web.security.web.authentications;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.itis.web.security.details.UserDetailsImpl;

import java.util.Collection;

@Data
public class CustomUsernamePasswordAuthentication implements Authentication {

    private String email;
    private UserDetailsImpl userDetails;
    private boolean isAuthenticated;

    public CustomUsernamePasswordAuthentication(String email) {
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
        return userDetails.getUserDTO();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        isAuthenticated = b;
    }

    @Override
    public String getName() {
        return email;
    }
}

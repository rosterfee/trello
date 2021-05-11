package ru.itis.web.security.web.authentications;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.itis.web.security.details.UserDetailsImpl;

import java.util.Collection;

@Data
public class VkOauthAuthentication implements Authentication {

    private UserDetailsImpl userDetails;
    private boolean isAuthenticated;
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        if (userDetails == null) {
            return null;
        }
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

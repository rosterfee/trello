package ru.itis.web.security.api.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import ru.itis.web.security.api.authentications.JwtAuthentication;
import ru.itis.web.security.details.UserDetailsImpl;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("customUserDetailService")
    private UserDetailsService userDetailsService;

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthentication.class.equals(aClass);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        jwtAuthentication.setAuthenticated(true);
        System.out.println(authentication.getName());
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                .loadUserByUsername(authentication.getName());
        jwtAuthentication.setUserDetails(userDetails);
        System.out.println(jwtAuthentication.getDetails());
        return jwtAuthentication;
    }

}

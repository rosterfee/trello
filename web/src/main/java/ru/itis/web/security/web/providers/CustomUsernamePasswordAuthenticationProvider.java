package ru.itis.web.security.web.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.web.security.details.UserDetailsImpl;
import ru.itis.web.security.web.authentications.CustomUsernamePasswordAuthentication;

@Component
public class CustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("customUserDetailService")
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();

        CustomUsernamePasswordAuthentication customAuthentication =
                new CustomUsernamePasswordAuthentication(email);
        UserDetailsImpl userDetails = (UserDetailsImpl)
                userDetailsService.loadUserByUsername(email);

        customAuthentication.setUserDetails(userDetails);
        customAuthentication.setAuthenticated(true);

        return customAuthentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

}

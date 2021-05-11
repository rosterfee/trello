package ru.itis.web.security.web.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.web.security.web.authentications.VkOauthAuthentication;
import ru.itis.web.security.details.UserDetailsImpl;

@Component
public class VkOauthAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("customUserDetailService")
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        VkOauthAuthentication vkAuthentication = (VkOauthAuthentication) authentication;
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                .loadUserByUsername(authentication.getName());

        vkAuthentication.setUserDetails(userDetails);
        vkAuthentication.setAuthenticated(true);

        return vkAuthentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(VkOauthAuthentication.class);
    }

}

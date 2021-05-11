package ru.itis.web.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.impl.entities.User;
import ru.itis.impl.repositories.UsersRepository;

@Component("customUserDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.getByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with such email not found"));
        return new UserDetailsImpl(user);
    }

}

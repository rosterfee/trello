package ru.itis.web.security.api.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.web.security.api.filters.JwtAccessAuthenticationFilter;
import ru.itis.web.security.api.filters.JwtRefreshAuthenticationFilter;
import ru.itis.web.security.api.providers.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
@Order(2)
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtRefreshAuthenticationFilter jwtRefreshAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .mvcMatcher("/api")
                .authorizeRequests()
                .antMatchers("/auth/*").permitAll()
                .antMatchers("/methods/**", "/methods/*").authenticated()
                .antMatchers("/methods/boards/**").hasAuthority("ADMIN")
                    .and()
                .addFilterAfter(new JwtAccessAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtRefreshAuthenticationFilter,
                        JwtAccessAuthenticationFilter.class)
                .sessionManagement().disable()
                .csrf().disable();
    }
}

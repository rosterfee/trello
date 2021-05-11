package ru.itis.web.security.web.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.web.security.web.filters.LogoutFilter;
import ru.itis.web.security.web.filters.UserConfirmedFilter;
import ru.itis.web.security.web.providers.CustomUsernamePasswordAuthenticationProvider;
import ru.itis.web.security.web.providers.VkOauthAuthenticationProvider;

@EnableWebSecurity
@Configuration
@Order(1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "customUserDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserConfirmedFilter userConfirmedFilter;

    @Autowired
    private VkOauthAuthenticationProvider vkOauthAuthenticationProvider;

    @Autowired
    private CustomUsernamePasswordAuthenticationProvider
                        customUsernamePasswordAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/main", "/sign_up/**",
                        "/login").permitAll()
                .antMatchers("/home/**","/home/*", "boards/**",
                        "/boards/*", "/cards/**", "/cards/*").authenticated()
                    .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?status=error")
                    .and()
                .logout()
                .logoutUrl("/log_out")
                .logoutSuccessUrl("/login")
                    .and()
                .addFilterAfter(userConfirmedFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new LogoutFilter(),
                        UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(vkOauthAuthenticationProvider);
        auth.authenticationProvider(customUsernamePasswordAuthenticationProvider);
    }

}

package ru.itis.web.security.api.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.api.dtos.web.JwtTokens;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.services.UsersService;
import ru.itis.impl.entities.RefreshToken;
import ru.itis.web.security.api.authentications.JwtAuthentication;
import ru.itis.web.security.api.services.JwtAuthenticationService;
import ru.itis.web.security.api.services.RefreshTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtRefreshAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private UsersService usersService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("refresh working");

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        System.out.println(authentication);

        if (authentication == null) {

            String refreshTokenUUID = httpServletRequest.getHeader("refresh-token");
            Optional<RefreshToken> optionalRefreshToken = refreshTokenService
                    .getByUUID(refreshTokenUUID);

            if (optionalRefreshToken.isPresent()) {
                RefreshToken refreshToken = optionalRefreshToken.get();

                if (refreshToken.getExpires_at().getTime() < new Date().getTime())
                    httpServletResponse.sendError(403, "Refresh token expired");
                else {

                    refreshTokenService.delete(refreshToken);

                    UserDTO userDto = usersService.getById(refreshToken.getUserId());
                    JwtTokens jwtTokens = jwtAuthenticationService.createTokens(userDto);
                    httpServletResponse.setHeader("access-token",
                            jwtTokens.getAccessToken());
                    httpServletResponse.setHeader("refresh-token",
                            jwtTokens.getRefreshToken());

                    authentication = new JwtAuthentication(userDto.getEmail());
                    securityContext.setAuthentication(authentication);
                    System.out.println("auth: " + securityContext.getAuthentication());
                }
            }
            else httpServletResponse.sendError(403, "Invalid refresh token");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean shouldFilter = request.getRequestURI().startsWith("/api/methods");
        return !shouldFilter;
    }

}

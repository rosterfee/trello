package ru.itis.web.security.api.filters;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import ru.itis.web.security.api.authentications.JwtAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAccessAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("access working");

        String accessTokenValue = httpServletRequest.getHeader("access-token");

        try {
            JWTVerifier verifier = JWT.require(
                    Algorithm.HMAC256("damir_habirovich")).build();
            DecodedJWT decodedJWT = verifier.verify(accessTokenValue);

            Authentication jwtAuthentication = new JwtAuthentication(
                    decodedJWT.getClaim("email").toString());
            System.out.println(jwtAuthentication.getName());
            SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);

        } catch (JWTVerificationException e) {
            if (!(e instanceof TokenExpiredException)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong token", e);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean shouldFilter = request.getRequestURI().startsWith("/api/methods");
        return !shouldFilter;
    }
}

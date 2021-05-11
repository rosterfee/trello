package ru.itis.web.security.web.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class UserConfirmedFilter extends OncePerRequestFilter {

    @Autowired
    private List<String> forConfirmedOnlyUris;

    private Authentication authentication;

    @Override
    protected void initFilterBean() throws ServletException {
        authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (!authorities.contains("CONFIRMED")) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/sign_up/pls_confirm_email");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        if (authentication == null) {
            return true;
        }
        String currentUri = request.getRequestURI();
        for (String uri: forConfirmedOnlyUris) {
            if (currentUri.startsWith(uri)) {
                return false;
            }
        }
        return true;
    }
}

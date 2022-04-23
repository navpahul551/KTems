package com.navpahul.KTems.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.navpahul.KTems.data.CustomUserDetails;
import com.navpahul.KTems.entities.User;
import com.navpahul.KTems.services.UserService;

import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            System.out.println("jwt filter...");
            initializeBeans(request);
            String jwtToken = getJwtToken(request.getHeader("Authorization"));

            // validate token instead of login
            if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {
                if(jwtTokenProvider.validateToken(jwtToken)){
                    System.out.println("jwt token is valid. Now logging the user....");

                    Long userId = jwtTokenProvider.getUserIdFromJwtToken(jwtToken);

                    User userDetails = userService.getUserById(userId);

                    Hibernate.initialize(userDetails.getRoles());

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    
                    // authenticating the user if not already signed in
                    if(!(authentication != null && authentication.getPrincipal().equals(userDetails.getUsername()))){
                        userService.authenticateUser(new CustomUserDetails(userDetails));
                    }

                    filterChain.doFilter(request, response);            
                }
                else{
                    System.out.println("Request is unauthorized....");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
            else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getJwtToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private void initializeBeans(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getWebApplicationContext(servletContext);
        jwtTokenProvider = webApplicationContext.getBean(JwtTokenProvider.class);
        userService = webApplicationContext.getBean(UserService.class);
    }
}

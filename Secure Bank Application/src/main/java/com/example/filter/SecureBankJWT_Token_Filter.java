package com.example.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class SecureBankJWT_Token_Filter extends OncePerRequestFilter {
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null)
        {
            Environment env=getEnvironment();
            if(null!=env)
            {
                String secret=env.getProperty("JWT","jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4");
                SecretKey secretKey= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String JWT=Jwts.builder().issuer("SecureBank").subject("JWT Token")
                        .claim("Username",authentication.getName())
                        .claim("authorities",authentication.getAuthorities()
                                .stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime()+3000000))
                        .signWith(secretKey).compact();
                response.setHeader("Authorization",JWT);

            }
            filterChain.doFilter(request,response);
        }
    }
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }
}

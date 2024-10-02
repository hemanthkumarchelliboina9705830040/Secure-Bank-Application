package com.example.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
@Slf4j
public class SecureBankCustomerLoggingFilter implements Filter {
    /**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
            System.out.println("The User Name : =" + authentication.getName() + " "
                    + "Is Successfully Authenticated With Authorities = " + authentication.getAuthorities().toString());
        }
filterChain.doFilter(servletRequest,servletResponse);
    }
}

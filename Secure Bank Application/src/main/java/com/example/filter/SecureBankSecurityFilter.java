package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SecureBankSecurityFilter implements Filter {
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
                         FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String header=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header!=null)
        {
            header=header.trim();
            if(StringUtils.startsWithIgnoreCase(header,"Basic"))
            {
                byte[] base64token=header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decode;
                try
                {
                 decode= Base64.getDecoder().decode(base64token);
                 String token=new String(decode,StandardCharsets.UTF_8);
                 int index=token.indexOf(":");
                 if(index==-1)
                 {
                     throw new BadCredentialsException("entered credential are invalid");
                 }
                 String username=token.substring(0,index);
                 if(username.toLowerCase().contains("test"))
                 {
                     response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                     return;
                 }
                }
                catch(IllegalArgumentException ex)
                {
                    throw new IllegalArgumentException("Entered credentials are invalid");
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}

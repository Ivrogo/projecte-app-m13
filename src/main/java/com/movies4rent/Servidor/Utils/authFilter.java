package com.movies4rent.Servidor.Utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class authFilter implements Filter {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();

        if(path.startsWith("/swagger")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        if (path.equals("/register")) {
            chain.doFilter(request, response);
            return;
        }

    }

    @Override
    public void destroy() {
    }

    private void handleInvalidKey(HttpServletResponse response) {
        response.setStatus(401);
        try {
            response.getOutputStream().write(new String("Invalid AppKey").getBytes());
        } catch (IOException e) {

        }
    }

    private void handleInvalidCredentials(HttpServletResponse response) {
        response.setStatus(401);
        try {
            response.getOutputStream().write(new String("Invalid token").getBytes());
        } catch (IOException e) {

        }
    }
}
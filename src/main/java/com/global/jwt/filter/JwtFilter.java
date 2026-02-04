package com.global.jwt.filter;

import java.io.IOException;

import com.global.jwt.service.JwtService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/api/*")
public class JwtFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) rq;
        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            ((HttpServletResponse) rs).setStatus(401);
            return;
        }
        String token = header.substring(7);
        var decoded = JwtService.verify(token);
        if (decoded == null) {
            ((HttpServletResponse) rs).setStatus(401);
            return;
        }
        String userId = decoded.getSubject();
        req.setAttribute("userId", userId);
        chain.doFilter(rq, rs);
    }
}


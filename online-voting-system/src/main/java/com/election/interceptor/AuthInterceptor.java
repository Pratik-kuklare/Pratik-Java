package com.election.interceptor;

import com.election.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        
        // Allow access to public pages
        if (uri.equals("/") || uri.equals("/login") || uri.equals("/register") || 
            uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/test") || 
            uri.equals("/error")) {
            return true;
        }
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // Check if user is logged in
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }
        
        // Check role-based access
        if (uri.startsWith("/admin/") && !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("/login");
            return false;
        }
        
        if (uri.startsWith("/voter/") && !"VOTER".equals(user.getRole())) {
            response.sendRedirect("/login");
            return false;
        }
        
        return true;
    }
}
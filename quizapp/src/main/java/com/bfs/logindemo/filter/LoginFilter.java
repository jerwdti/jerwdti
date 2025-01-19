package com.bfs.logindemo.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bfs.logindemo.domain.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
@Component
public class LoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("Request path: " + path);

        HttpSession session = request.getSession(false);

        if (shouldAllowAdminAccess(path, session)) {
            System.out.println("Admin is accessing the admin page. Proceeding with request.");
            filterChain.doFilter(request, response);
            return;
        }

        if (session != null && session.getAttribute("user") != null) {
            System.out.println("User is logged in. Proceeding with request.");
            filterChain.doFilter(request, response);
        } else {
            System.out.println("User not logged in. Redirecting to login page.");
            response.sendRedirect("/login");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        System.out.println("Evaluating filter exclusion for path: " + path);
        return "/login".equals(path) || "/WEB-INF/jsp/login.jsp".equals(path) ||
                "/register".equals(path) || "/WEB-INF/jsp/register.jsp".equals(path);
    }

    private boolean shouldAllowAdminAccess(String path, HttpSession session) {
        if (path.startsWith("/admin")) {
            if (session != null) {
                User user = (User) session.getAttribute("user");
                return user.isAdmin();
            }
            return false;
        }
        return false;
    }
}
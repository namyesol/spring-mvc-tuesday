package com.namyesol.tuesday.infrastructure.springframework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.namyesol.tuesday.controller.constant.SessionConstants;

public class AuthenticationInterceptor implements HandlerInterceptor {
	
	private final static Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String contextPath = request.getServletContext().getContextPath();
        String requestURI = request.getRequestURI().replaceFirst(contextPath, "");
        
        log.debug("intercept requestURI=[{}] ",requestURI);
        
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConstants.AUTHENTICATED_MEMBER) == null) {
            response.sendRedirect(contextPath + "/login?redirect=" + requestURI);
        }

        return true;
    }

}

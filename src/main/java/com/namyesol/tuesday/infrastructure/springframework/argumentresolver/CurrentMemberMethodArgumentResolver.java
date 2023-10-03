package com.namyesol.tuesday.infrastructure.springframework.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.namyesol.tuesday.controller.constant.SessionConstants;
import com.namyesol.tuesday.domain.member.Member;

/**
 * Resolves method arguments annotated with an @{@link CurrentMember}.
 *
 * @author NamYesol
 */
public class CurrentMemberMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasCurrentMemberAnnotation = parameter.hasParameterAnnotation(CurrentMember.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasCurrentMemberAnnotation && hasMemberType;
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        
        if (session == null) {
            throw new IllegalStateException("Authenticated Member does not exists");
        }
        
        Member member = (Member) session.getAttribute(SessionConstants.AUTHENTICATED_MEMBER);
        
        if (member == null) {
        	throw new IllegalStateException("Authenticated Member does not exists");
        }
        
        return member;
    }
   
}

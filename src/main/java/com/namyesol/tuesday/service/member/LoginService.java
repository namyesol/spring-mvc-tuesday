package com.namyesol.tuesday.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.exception.UnauthorizedException;

@Service
@Transactional(readOnly = true)
public class LoginService {

    private final MemberService memberService;

    public LoginService(MemberService memberService) {
        this.memberService = memberService;
    }

    public Member login(String username, String password) {

        Member member = memberService.findMemberByUsername(username);

        if (member == null || !password.equals(member.getPassword())) {
        	return null;
//            throw new UnauthorizedException("Invalid username or password"); 
        }

        return member;
    }
}

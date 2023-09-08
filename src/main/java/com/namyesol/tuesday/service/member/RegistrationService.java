package com.namyesol.tuesday.service.member;

import org.springframework.stereotype.Service;

import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.exception.AlreadyExistsException;

@Service
public class RegistrationService {

    private MemberService memberService;

    public RegistrationService(MemberService memberService) {
        this.memberService = memberService;
    }

    public void register(Member member) {
        if (existsByUsername(member.getUsername()) || existsByEmail(member.getEmail()) ) {
            throw new AlreadyExistsException();
        }
        
        memberService.createMember(member);
    }

    private boolean existsByUsername(String username) {
        Member member = memberService.findMemberByUsername(username);
        return member != null;
    }

    private boolean existsByEmail(String email) {
        Member member = memberService.findMemberByEmail(email);
        return member != null;
    }
    
}

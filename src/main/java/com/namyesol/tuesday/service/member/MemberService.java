package com.namyesol.tuesday.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.exception.NotFoundException;
import com.namyesol.tuesday.mapper.MemberMapper;

@Service
public class MemberService {
    
    public final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public void createMember(Member member) {
        memberMapper.insert(member);
    }

    public Member findMemberById(Long id) {
        return memberMapper.findById(id);
    }

    public Member findMemberByUsername(String username) {
        return memberMapper.findByUsername(username);
    }

    public Member findMemberByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    public void updateMember(Member member) {
        memberMapper.update(member);
    }

    public void deleteMember(Long id) {
    	Member member = memberMapper.findById(id);
    	
    	if (member == null) {
    		throw new NotFoundException();
    	}
    	
        memberMapper.delete(id);
    }

}

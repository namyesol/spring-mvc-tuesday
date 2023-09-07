package com.namyesol.tuesday.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.namyesol.tuesday.domain.member.Member;

@Mapper
public interface MemberMapper {

    void insert(Member member);

    Member findByUsername(String username);

    Member findByEmail(String email);

    Member findById(Long id);

    List<Member> findAll();

    void update(Member member);

    void delete(Long id);
}

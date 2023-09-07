package com.namyesol.tuesday.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.mapper.MemberMapper;

@Repository
public class MemberDao implements MemberMapper {

	private final SqlSessionTemplate sqlSessionTemplate;

	private static final String namespace = "com.namyesol.tuesday.mapper.MemberMapper";

	public MemberDao(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public void insert(Member member) {
		sqlSessionTemplate.insert(namespace+".insert", member);
	}

	@Override
	public Member findByUsername(String username) {
		return sqlSessionTemplate.selectOne(namespace + ".findByUsername", username);
	}

	@Override
	public Member findByEmail(String email) {
		return sqlSessionTemplate.selectOne(namespace + ".findByEmail", email);
		
	}

	@Override
	public Member findById(Long id) {
		return sqlSessionTemplate.selectOne(namespace + ".findById", id);
	}

	@Override
	public List<Member> findAll() {
		return sqlSessionTemplate.selectList(namespace + ".findAll");
	}

	@Override
	public void update(Member member) {
		sqlSessionTemplate.update(namespace + ".update", member);
	}

	@Override
	public void delete(Long id) {
		sqlSessionTemplate.delete(namespace + ".delete", id);
	}
	
	
}

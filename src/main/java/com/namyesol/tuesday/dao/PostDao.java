package com.namyesol.tuesday.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.namyesol.tuesday.domain.board.Post;
import com.namyesol.tuesday.mapper.PostMapper;

@Repository
public class PostDao implements PostMapper {

	private final SqlSessionTemplate sqlSessionTemplate;

	private static final String namespace = "com.namyesol.tuesday.mapper.PostMapper";

	public PostDao(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public void insert(Post post) {
		sqlSessionTemplate.insert(namespace + ".insert", post);
	}

	@Override
	public Post findById(Long id) {
		return sqlSessionTemplate.selectOne(namespace + ".findById", id);
	}

	@Override
	public List<Post> findByMemberId(Long id) {
		return sqlSessionTemplate.selectList(namespace + ".findByMemberId", id);
	}

	@Override
	public List<Post> findAll() {
		return sqlSessionTemplate.selectList(namespace + ".findAll");
	}

	@Override
	public void update(Post post) {
		sqlSessionTemplate.update(namespace + ".update", post);
	}

	@Override
	public void delete(Long id) {
		sqlSessionTemplate.delete(namespace + ".delete", id);
	}

	@Override
	public void increaseViews(Long id) {
		sqlSessionTemplate.update(namespace + ".increaseViews", id);
	}
	
	
	
}

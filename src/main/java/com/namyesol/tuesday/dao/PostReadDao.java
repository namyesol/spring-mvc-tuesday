package com.namyesol.tuesday.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.namyesol.tuesday.dto.post.CommentDetails;
import com.namyesol.tuesday.dto.post.PostDetails;
import com.namyesol.tuesday.mapper.PostReadMapper;

@Repository
public class PostReadDao implements PostReadMapper {

	private final SqlSessionTemplate sqlSessionTemplate;

	private static final String namespace = "com.namyesol.tuesday.mapper.PostReadMapper";

	
	public PostReadDao(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}


	@Override
	public PostDetails findById(Long id) {
		return sqlSessionTemplate.selectOne(namespace + ".findById", id);
	}


	@Override
	public List<PostDetails> findAll() {
		return sqlSessionTemplate.selectList(namespace + ".findAll");
	}


	@Override
	public List<CommentDetails> findCommentsByPostId(Long postId) {
		return sqlSessionTemplate.selectList(namespace + ".findCommentsByPostId", postId);
	}

}

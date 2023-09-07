package com.namyesol.tuesday.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.namyesol.tuesday.domain.board.Comment;
import com.namyesol.tuesday.mapper.CommentMapper;

@Repository
public class CommentDao implements CommentMapper {

	private final SqlSessionTemplate sqlSessionTemplate;

	private static final String namespace = "com.namyesol.tuesday.mapper.CommentMapper";
	
	public CommentDao(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public void insert(Comment comment) {
		sqlSessionTemplate.insert(namespace + ".insert", comment);
	}

	@Override
	public Comment findById(Long id) {
		return sqlSessionTemplate.selectOne(namespace + ".findById", id);
	}

	@Override
	public List<Comment> findCommentsByPostId(Long postId) {
		return sqlSessionTemplate.selectList(namespace + ".findCommentsByPostId", postId);
	}

	@Override
	public void update(Comment comment) {
		sqlSessionTemplate.update(namespace + ".update", comment);
	}

	@Override
	public void delete(Long id) {
		sqlSessionTemplate.delete(namespace + ".delete", id);
	}
	
	
	
}

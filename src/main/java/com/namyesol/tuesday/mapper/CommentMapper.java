package com.namyesol.tuesday.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.namyesol.tuesday.domain.board.Comment;

@Mapper
public interface CommentMapper {
    
    void insert(Comment comment);

    Comment findById(Long id);

    List<Comment> findCommentsByPostId(Long postId);

    void update(Comment comment);

    void delete(Long id);

}

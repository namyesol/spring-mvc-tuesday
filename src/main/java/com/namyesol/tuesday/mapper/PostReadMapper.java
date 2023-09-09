package com.namyesol.tuesday.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.namyesol.tuesday.dto.post.CommentDetails;
import com.namyesol.tuesday.dto.post.PostDetails;

@Mapper
public interface PostReadMapper {
	
    PostDetails findById(Long id);
    
    List<PostDetails> findAll();
    
    List<CommentDetails> findCommentsByPostId(Long postId);
}

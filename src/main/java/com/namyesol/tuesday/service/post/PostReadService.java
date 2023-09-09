package com.namyesol.tuesday.service.post;

import java.util.List;

import org.springframework.stereotype.Service;

import com.namyesol.tuesday.dto.post.CommentDetails;
import com.namyesol.tuesday.dto.post.PostDetails;
import com.namyesol.tuesday.mapper.PostReadMapper;

@Service
public class PostReadService {

    public final PostReadMapper postReadMapper;
    
    public PostReadService(PostReadMapper postReadMapper) {
        this.postReadMapper = postReadMapper;
    }

    public PostDetails findById(Long id) {
        return postReadMapper.findById(id); 
    }

    public List<PostDetails> findAll() {
        return postReadMapper.findAll();
    }

    public List<CommentDetails> findCommentsByPostId(Long id) {
        return postReadMapper.findCommentsByPostId(id);
    }
    
}

package com.namyesol.tuesday.service.post;

import java.util.List;

import org.springframework.stereotype.Service;

import com.namyesol.tuesday.domain.post.Comment;
import com.namyesol.tuesday.exception.UnauthorizedException;
import com.namyesol.tuesday.mapper.CommentMapper;

@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public void save(Comment comment) {
        commentMapper.insert(comment);
    }
    
    public Comment findById(Long id) {
    	return commentMapper.findById(id);
    }

    public List<Comment> findCommentsByPostId(Long postId) {
        return commentMapper.findCommentsByPostId(postId);
    }
    
    public void update(Long commentId, Long memberId, Comment updateParam) {
    	Comment comment = commentMapper.findById(commentId);
    	
    	if (!comment.getMemberId().equals(memberId)) {
    		throw new UnauthorizedException();
    	}
    	
    	comment.setContent(updateParam.getContent());
    	
    	commentMapper.update(comment);
    }
    
    public void delete(Long commentId, Long memberId) {
    	
    	Comment comment = commentMapper.findById(commentId);
    	
    	if (!comment.getMemberId().equals(memberId)) {
    		throw new UnauthorizedException();
    	}
    	
    	commentMapper.delete(commentId);
    }

}

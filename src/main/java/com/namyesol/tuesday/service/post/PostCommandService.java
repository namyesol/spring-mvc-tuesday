package com.namyesol.tuesday.service.post;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.namyesol.tuesday.domain.post.Post;
import com.namyesol.tuesday.exception.UnauthorizedException;
import com.namyesol.tuesday.mapper.PostMapper;

@Service
public class PostCommandService {

    private final PostMapper postMapper;

    public PostCommandService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Transactional
    public void save(Post post) {
        postMapper.insert(post);
    }

    @Transactional(readOnly=true)
    public Post findByPostId(Long postId) {
        Post post = postMapper.findById(postId);
        return post;
    }

    @Transactional(readOnly=true)
    public List<Post> findAll() {
        return postMapper.findAll();
    }

    @Transactional
    public void update(Long postId, Long memberId, Post updateParam) {
        
        Post post = postMapper.findById(postId);

        if (!post.getMemberId().equals(memberId)) {
        	throw new UnauthorizedException();
        }
        
        post.setTitle(updateParam.getTitle());
        post.setContent(updateParam.getContent());

        postMapper.update(post);
    }

    @Transactional
    public void deletePost(Long postId, Long memberId) {
        Post post = postMapper.findById(postId);

        if (!post.getMemberId().equals(memberId)) {
            throw new UnauthorizedException();
        }

        postMapper.delete(postId);
    }
    
    public void increaseViews(Long postId) {
    	postMapper.increaseViews(postId);
    }
}

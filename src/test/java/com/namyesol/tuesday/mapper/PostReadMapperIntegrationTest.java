package com.namyesol.tuesday.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.domain.post.Comment;
import com.namyesol.tuesday.domain.post.Post;
import com.namyesol.tuesday.dto.post.CommentDetails;
import com.namyesol.tuesday.dto.post.PostDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/application-context.xml"
})
@Transactional
public class PostReadMapperIntegrationTest {
	
	@Autowired
    MemberMapper memberMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    CommentMapper commentMapper;
	
    @Autowired
    PostReadMapper postReadMapper;
    
    Member member;
    Post post;
    
    @Before
    public void setUp() {
    	member = new Member("test", "test", "test@test.com");
        memberMapper.insert(member);
        post = new Post(member.getId(), "test-title", "test-content");
        postMapper.insert(post);
    }
    
	@Test
	public void findById() {
		PostDetails postDetails = postReadMapper.findById(post.getId());
		
		assertThat(postDetails)
        .returns(post.getId(), from(PostDetails::getId))
        .returns(post.getTitle(), from(PostDetails::getTitle))
        .returns(post.getContent(), from(PostDetails::getContent))
        .returns(post.getCreatedAt(), from(PostDetails::getCreatedAt))
        .returns(post.getUpdatedAt(), from(PostDetails::getUpdatedAt))
        .returns(member.getId(), from(PostDetails::getAuthorId))
        .returns(member.getUsername(), from(PostDetails::getAuthorName));
    
	}
	
	@Test
	public void findCommentsByPostId() {
		// given
		Comment comment = new Comment(member.getId(), post.getId(), "test-comment");
        Comment comment2 = new Comment(member.getId(), post.getId(), "test-comment2");
        commentMapper.insert(comment);
        commentMapper.insert(comment2);

        
        // when
        List<CommentDetails> commentDetailsList = postReadMapper.findCommentsByPostId(post.getId());
        
        // then
        Assertions.assertThat(commentDetailsList).hasSize(2);
        CommentDetails commentDetails = commentDetailsList.get(0);
        
        assertThat(commentDetails)
        .returns(comment.getId(), from(CommentDetails::getId))
        .returns(comment.getPostId(), from(CommentDetails::getPostId))
        .returns(comment.getContent(), from(CommentDetails::getContent))
        .returns(comment.getCreatedAt(), from(CommentDetails::getCreatedAt))
        .returns(comment.getUpdatedAt(), from(CommentDetails::getUpdatedAt))
        .returns(member.getId(), from(CommentDetails::getAuthorId))
        .returns(member.getUsername(), from(CommentDetails::getAuthorName));

	}
	
	
}

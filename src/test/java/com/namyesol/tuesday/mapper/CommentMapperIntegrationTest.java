package com.namyesol.tuesday.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.domain.post.Comment;
import com.namyesol.tuesday.domain.post.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/application-context.xml"})
@Transactional
public class CommentMapperIntegrationTest {

    private final static Logger log = LoggerFactory.getLogger(CommentMapperIntegrationTest.class);

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    CommentMapper commentMapper;

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
    public void insert() {
        log.debug("member={}, post={}", member, post);
        //given
        Comment comment = new Comment(member.getId(), post.getId(), "test-comment");
        
        //when
        commentMapper.insert(comment);

        // then
        assertThat(comment.getId()).isNotNull();
    }

    @Test
    public void findById() {
        // given
        Comment comment = new Comment(member.getId(), post.getId(), "test-comment");
        commentMapper.insert(comment);
        // when
        Comment savedComment = commentMapper.findById(comment.getId());

        // then
        assertThat(savedComment)
                .returns(comment.getId(), from(Comment::getId))
                .returns(comment.getMemberId(), from(Comment::getMemberId))
                .returns(comment.getPostId(), from(Comment::getPostId))
                .returns(comment.getContent(), from(Comment::getContent))
                .returns(comment.getCreatedAt(), from(Comment::getCreatedAt))
                .returns(comment.getUpdatedAt(), from(Comment::getUpdatedAt));

    }

    @Test
    public void findByPostId() {
        // given
        Comment comment = new Comment(member.getId(), post.getId(), "test-comment");
        Comment comment2 = new Comment(member.getId(), post.getId(), "test-comment2");
        commentMapper.insert(comment);
        commentMapper.insert(comment2);

        Post post2 = new Post(member.getId(), "new-post", "new-post-content");
        postMapper.insert(post2);
        Comment comment3 = new Comment(member.getId(), post2.getId(), "test-comment3");
        commentMapper.insert(comment3);

        // when
        List<Comment> comments = commentMapper.findCommentsByPostId(post.getId());
        
        assertThat(comments).hasSize(2).contains(comment, comment2);
        for (Comment c : comments) {
            assertThat(c).hasNoNullFieldsOrProperties();
        }
    }

    @Test
    public void udpate() {
        // given
        Comment comment = new Comment(member.getId(), post.getId(), "test-comment");
        commentMapper.insert(comment);
        
        // when
        comment.setContent("update-comment");
        commentMapper.insert(comment);

        // then
        Comment updatedComment = commentMapper.findById(comment.getId());

        assertThat(updatedComment.getContent()).isEqualTo("update-comment");
        assertThat(updatedComment.getUpdatedAt()).isAfter(updatedComment.getCreatedAt());
    }

    @Test
    public void delete() {
        // given
        Comment comment = new Comment(member.getId(), post.getId(), "test-comment");
        commentMapper.insert(comment);
        
        // when
        commentMapper.delete(comment.getId());

        // then
        Comment deletedComment = commentMapper.findById(comment.getId());

        assertThat(deletedComment).isNull();
    }

}

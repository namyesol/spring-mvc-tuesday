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

import com.namyesol.tuesday.domain.board.Post;
import com.namyesol.tuesday.domain.member.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/application-context.xml"
})
@Transactional
public class PostMapperIntegrationTest {
    
    private static final Logger log = LoggerFactory.getLogger(PostMapperIntegrationTest.class);
    
    @Autowired
    PostMapper postMapper;

    @Autowired
    MemberMapper memberMapper;

    Member member;

    @Before
    public void setUp() {
        member = createMember("test");
        memberMapper.insert(member);
    }

    @Test
    public void insertPost() {
        // given
        Long memberId = member.getId(); 
        String title = "안녕하세요";
        String content = "첫 번째 글입니다.";
        
        Post post = new Post(memberId, title, content);
        log.debug("Post={}", post);
        
        // when
        postMapper.insert(post);

        // then
        assertThat(post.getId()).isNotNull();
        log.debug("Post={}", post);
    }

    @Test
    public void findById() {
        // given
        Post post = new Post(member.getId(), "title", "content");
        postMapper.insert(post);
        
        // when
        Post savedPost = postMapper.findById(post.getId());

        assertThat(savedPost)
            .returns(post.getId(), from(Post::getId))
            .returns(post.getMemberId(), from(Post::getMemberId))
            .returns(post.getTitle(), from(Post::getTitle))
            .returns(post.getContent(), from(Post::getContent))
            .returns(post.getCreatedAt(), from(Post::getCreatedAt))
            .returns(post.getUpdatedAt(), from(Post::getUpdatedAt));
        
    }

    @Test
    public void findByMemberId() {
        // given
        Post post = new Post(member.getId(), "title", "content");
        Post post2 = new Post(member.getId(), "title2", "content2");
        Post post3 = new Post(member.getId()+1L, "title3", "content3");
        
        postMapper.insert(post);
        postMapper.insert(post2);
        postMapper.insert(post3);
        
        // when
        List<Post> posts = postMapper.findByMemberId(member.getId());

        assertThat(posts).hasSize(2).contains(post, post2);

    }

    @Test
    public void findAll() {
        // given
        Post post1 = new Post(member.getId(), "title", "content");
        Post post2 = new Post(member.getId(), "title2", "content2");
        Post post3 = new Post(member.getId()+1L, "title3", "content3");
        
        postMapper.insert(post1);
        postMapper.insert(post2);
        postMapper.insert(post3);

        // when
        List<Post> posts = postMapper.findAll();
        
        log.debug("Posts={}", posts);
        // then
        assertThat(posts).contains(post1, post2, post3);
        
    }

    @Test
    public void updatePost() {
        // given
        Post post = new Post(member.getId(), "title", "content");
        postMapper.insert(post);
        
        // when
        post.setTitle("update-title");
        post.setContent("update-content");
        postMapper.update(post);

        // then
        Post updatedPost = postMapper.findById(post.getId());

        assertThat(updatedPost.getId()).isEqualTo(post.getId());
        assertThat(updatedPost.getTitle()).isEqualTo("update-title");
        assertThat(updatedPost.getContent()).isEqualTo("update-content");
        assertThat(updatedPost.getUpdatedAt()).isAfterOrEqualTo(post.getUpdatedAt());
    }

    @Test
    public void deletePost() {
        // given
        Post post = new Post(member.getId(), "title", "content");
        postMapper.insert(post);
        
        // when
        postMapper.delete(post.getId());

        // then
        Post deletedPost = postMapper.findById(post.getId());

        assertThat(deletedPost).isNull();
    }

    private Member createMember(String username) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(username + "@example.com");
        member.setPassword("test");
        return member;
    }
}


package com.namyesol.tuesday.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.namyesol.tuesday.domain.member.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/application-context.xml"
})
@Transactional
public class MemberMapperIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(MemberMapperIntegrationTest.class);

    @Autowired
    MemberMapper memberMapper;

    @Test
    public void insertMember() {
        // given
        Member member = createMember("test");

        // when
        memberMapper.insert(member);

        // then
        assertThat(member.getId()).isNotNull();
        log.debug("member={}", member);
    }

    private Member createMember(String username) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(username + "@example.com");
        member.setPassword("test");
        return member;
    }

    @Test
    public void findByUsername() {
        // given
        Member member = createMember("test");
        memberMapper.insert(member);

        // when
        Member foundMember = memberMapper.findByUsername(member.getUsername());

        // then
        assertThat(foundMember)
                .returns(member.getId(), from(Member::getId))
                .returns(member.getEmail(), from(Member::getEmail))
                .returns(member.getUsername(), from(Member::getUsername))
                .returns(member.getPassword(), from(Member::getPassword));
    }

    @Test
    public void findByEmail() {
        // given
        Member member = createMember("test");
        memberMapper.insert(member);

        // when
        Member foundMember = memberMapper.findByEmail(member.getEmail());

        // then

        assertThat(foundMember)
                .returns(member.getId(), from(Member::getId))
                .returns(member.getEmail(), from(Member::getEmail))
                .returns(member.getUsername(), from(Member::getUsername))
                .returns(member.getPassword(), from(Member::getPassword));
    }

    @Test
    public void findById() {
        // given
        Member member = createMember("test");
        memberMapper.insert(member);

        // when
        Member foundMember = memberMapper.findById(member.getId());

        // then

        assertThat(foundMember)
                .returns(member.getId(), from(Member::getId))
                .returns(member.getEmail(), from(Member::getEmail))
                .returns(member.getUsername(), from(Member::getUsername))
                .returns(member.getPassword(), from(Member::getPassword));
    }

    @Test
    public void findAll() {
        // given
        Member member1 = createMember("aa");
        Member member2 = createMember("bb");

        memberMapper.insert(member1);
        memberMapper.insert(member2);

        // when
        List<Member> members = memberMapper.findAll();

        // then
        assertThat(members).contains(member1, member2);
    }

    @Test
    public void updateMember() {
        // given
        Member member = createMember("test");
        memberMapper.insert(member);

        // when
        member.setUsername("update");
        member.setEmail("update@example.com");
        member.setPassword("update");

        memberMapper.update(member);

        // then
        Member updatedMember = memberMapper.findById(member.getId());

        assertThat(updatedMember)
                .returns(member.getId(), from(Member::getId))
                .returns("update", from(Member::getUsername))
                .returns("update@example.com", from(Member::getEmail))
                .returns("update", from(Member::getPassword));
    }

    @Test
    public void deleteMember() {
        // given
        Member member = createMember("delete");
        memberMapper.insert(member);

        // when
        memberMapper.delete(member.getId());

        // then
        Member deletedMember = memberMapper.findById(member.getId());
        assertThat(deletedMember).isNull();
    }

}

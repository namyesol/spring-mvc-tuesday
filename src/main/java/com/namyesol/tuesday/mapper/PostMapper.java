package com.namyesol.tuesday.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.namyesol.tuesday.domain.board.Post;

@Mapper
public interface PostMapper {
    
    void insert(Post post);

    Post findById(Long id);

    List<Post> findByMemberId(Long id);

    List<Post> findAll();

    void update(Post post);

    void delete(Long id);

    void increaseViews(Long id);
}

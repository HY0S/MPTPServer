package com.gachon.mptpserver.Repository;

import com.gachon.mptpserver.DTO.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gachon.mptpserver.DTO.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Comment> findById(int postId);
}
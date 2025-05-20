package com.gachon.mptpserver.Service;


import com.gachon.mptpserver.DTO.Post;
import com.gachon.mptpserver.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void delete(int id) {
        postRepository.deleteById(id);
    }

    public Post getById(int id) {
        return postRepository.findById(id).orElse(null);
    }
}
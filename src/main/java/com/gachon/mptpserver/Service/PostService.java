package com.gachon.mptpserver.Service;

import com.gachon.mptpserver.DTO.Category;
import com.gachon.mptpserver.DTO.Post;
import com.gachon.mptpserver.Repository.CategoryRepository;
import com.gachon.mptpserver.Repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public void delete(int id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Post getById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    @Transactional
    public Post addCategoriesToPost(int postId, List<Category> categories) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        for (Category category : categories) {
            category.setPost(post);
            post.getCategories().add(category);
        }
        
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Category> getCategoriesByPostId(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        return post.getCategories();
    }
}
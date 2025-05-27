package com.gachon.mptpserver.Controller;


import com.gachon.mptpserver.DTO.Post;
import com.gachon.mptpserver.Repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 목록 조회
    @GetMapping("/list")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    // 게시글 단일 조회
    @GetMapping("/get/{id}")
    public ResponseEntity<Post> getPost(@PathVariable int id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Void> createPost(@RequestBody Post post) {
        postRepository.save(post);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable int id, @RequestParam String password) {
        return postRepository.findById(id).map(post -> {
            if (String.valueOf(post.getPassword()).equals(password)) {
                postRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(403).build(); // 비밀번호 불일치
            }
        }).orElse(ResponseEntity.notFound().build());
    }

}

package com.gachon.mptpserver.Controller;

import com.gachon.mptpserver.DTO.Category;
import com.gachon.mptpserver.DTO.Post;
import com.gachon.mptpserver.Service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 조회
    @GetMapping("/list")
    public List<Post> getPosts() {
        logger.info("게시글 목록 조회 요청");
        List<Post> posts = postService.getAllPosts();
        // 각 게시글의 카테고리 정보를 명시적으로 로드
        for (Post post : posts) {
            post.getCategories().size(); // 카테고리 정보를 강제로 로드
        }
        logger.info("게시글 목록 조회 완료, 총 {}개 게시글 반환", posts.size());
        return posts;
    }

    // 게시글 단일 조회
    @GetMapping("/get/{id}")
    public ResponseEntity<Post> getPost(@PathVariable int id) {
        logger.info("게시글 단일 조회 요청: id={}", id);
        Post post = postService.getById(id);
        if (post != null) {
            // 카테고리 정보를 명시적으로 로드
            post.getCategories().size();
            logger.info("게시글 조회 성공: id={}, 카테고리 수={}", id, post.getCategories().size());
            return ResponseEntity.ok(post);
        }
        logger.warn("게시글 조회 실패 - id={} 게시글 없음", id);
        return ResponseEntity.notFound().build();
    }

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        logger.info("게시글 작성 요청: title={}", post.getTitle());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        post.setDate(now.format(formatter));

        List<Category> categories = post.getCategories();
        post.setCategories(new ArrayList<>()); // 새로운 리스트로 초기화

        Post savedPost = postService.save(post); // 먼저 Post를 저장

        if (categories != null && !categories.isEmpty()) {
            logger.info("카테고리 수: {}", categories.size());
            for (Category category : categories) {
                Category newCategory = new Category(); // 새로운 Category 인스턴스 생성
                newCategory.setName(category.getName());
                newCategory.setCategory(category.getCategory());
                newCategory.setValue(category.getValue());
                newCategory.setDate(category.getDate());
                newCategory.setPost(savedPost);
                savedPost.getCategories().add(newCategory);
            }
        }

        savedPost = postService.save(savedPost); // 카테고리와 함께 다시 저장
        logger.info("게시글 저장 완료: id={}, 카테고리 수={}", 
            savedPost.getId(), 
            savedPost.getCategories() != null ? savedPost.getCategories().size() : 0);
        return ResponseEntity.ok(savedPost);
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable int id, @RequestParam String password) {
        logger.info("게시글 삭제 요청: id={}, password=******", id);
        Post post = postService.getById(id);
        
        if (post == null) {
            logger.warn("게시글 삭제 실패 - id={} 게시글 없음", id);
            return ResponseEntity.notFound().build();
        }
        
        if (!String.valueOf(post.getPassword()).equals(password)) { 
            logger.warn("게시글 삭제 실패 - 비밀번호 불일치: id={}", id);
            return ResponseEntity.status(403).build();
        }
        
        postService.delete(id);
        logger.info("게시글 삭제 성공: id={}", id);
        return ResponseEntity.ok().build();
    }

    // 게시글의 카테고리 목록 조회
    @GetMapping("/{postId}/categories")
    public ResponseEntity<List<Category>> getPostCategories(@PathVariable int postId) {
        logger.info("게시글 카테고리 목록 조회 요청: postId={}", postId);
        try {
            List<Category> categories = postService.getCategoriesByPostId(postId);
            logger.info("게시글 카테고리 목록 조회 완료: postId={}, 카테고리 수={}", postId, categories.size());
            return ResponseEntity.ok(categories);
        } catch (RuntimeException e) {
            logger.error("게시글 카테고리 목록 조회 실패: postId={}, error={}", postId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}

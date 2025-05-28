package com.gachon.mptpserver.Controller;

import com.gachon.mptpserver.DTO.Comment;
import com.gachon.mptpserver.Repository.CommentRepository;
import com.gachon.mptpserver.Repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 댓글 작성
    @PostMapping("/add/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable int postId, @RequestBody Comment comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post); // postId 대신 Post 객체 설정
            Comment saved = commentRepository.save(comment);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 게시글에 해당하는 댓글 조회
    @GetMapping("/get/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable int postId) {
        return commentRepository.findByPostId(postId);
    }
}

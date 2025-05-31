package com.gachon.mptpserver.Controller;

import com.gachon.mptpserver.DTO.Comment;
import com.gachon.mptpserver.Repository.CommentRepository;
import com.gachon.mptpserver.Repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 댓글 작성
    @PostMapping("/add/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable int postId, @RequestBody Comment comment) {
        logger.info("댓글 추가 요청: postId={}, comment={}", postId, comment);
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            Comment saved = commentRepository.save(comment);
            logger.info("댓글 저장 성공: {}", saved);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> {
            logger.warn("댓글 추가 실패 - postId={}에 해당하는 게시글 없음", postId);
            return ResponseEntity.notFound().build();
        });
    }

    // 게시글에 해당하는 댓글 조회
    @GetMapping("/get/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable int postId) {
        logger.info("댓글 조회 요청: postId={}", postId);
        List<Comment> comments = commentRepository.findByPostId(postId);
        logger.info("댓글 {}개 조회됨", comments.size());
        return comments;
    }
}

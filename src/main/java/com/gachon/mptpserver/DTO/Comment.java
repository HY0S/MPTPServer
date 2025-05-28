package com.gachon.mptpserver.DTO;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 외래키 관계 - Post 객체 참조 (권장)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "date")
    private LocalDateTime date;

    // 기본 생성자 (JPA 필수)
    public Comment() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    // 편의 메서드 - postId로도 접근 가능
    public int getPostId() {
        return post != null ? post.getId() : 0;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
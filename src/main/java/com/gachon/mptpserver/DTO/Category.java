package com.gachon.mptpserver.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "category", length = 255)
    private String category;

    @Column(name = "value")
    private Integer value;

    @Column(name = "date")
    private String date; // DATETIME 타입에 맞춤

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 기본 생성자
    public Category() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", value=" + value +
                ", date='" + date + '\'' +
                '}';
    }
}
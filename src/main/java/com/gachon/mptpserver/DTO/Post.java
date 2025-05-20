package com.gachon.mptpserver.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class Post {
    private int id;
    private String title;
    private String content;
    private String password;
    private String date;
    private List<Category> categories;
}
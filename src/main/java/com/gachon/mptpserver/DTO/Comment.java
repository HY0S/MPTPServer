package com.gachon.mptpserver.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Comment {
    private int id;
    private int postId;
    private String content;
    private String password;
    private String date;
}
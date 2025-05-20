package com.gachon.mptpserver.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Category {
    private String name;
    private String icon;
    private int value;
    private String date;
}
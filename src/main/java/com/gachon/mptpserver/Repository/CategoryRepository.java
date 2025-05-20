package com.gachon.mptpserver.Repository;


import com.gachon.mptpserver.DTO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
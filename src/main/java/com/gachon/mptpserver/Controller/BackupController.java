package com.gachon.mptpserver.Controller;

import com.gachon.mptpserver.DTO.Category;
import com.gachon.mptpserver.Repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BackupController {

    private final CategoryRepository categoryRepository;

    public BackupController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 백업
    @PostMapping("/backup")
    public ResponseEntity<Void> backupCategories(@RequestParam String id,
                                                 @RequestParam String password,
                                                 @RequestBody List<Category> categories) {
        // 실제 인증 로직은 생략
        categoryRepository.saveAll(categories);
        return ResponseEntity.ok().build();
    }

    // 복원
    @GetMapping("/Restore")
    public List<Category> restoreCategories(@RequestParam String id,
                                            @RequestParam String password) {
        return categoryRepository.findAll();
    }
}


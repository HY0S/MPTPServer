package com.gachon.mptpserver.Controller;

import com.gachon.mptpserver.DTO.BackupAuth;
import com.gachon.mptpserver.DTO.BackupCategory;
import com.gachon.mptpserver.DTO.Category;
import com.gachon.mptpserver.Repository.BackupAuthRepository;
import com.gachon.mptpserver.Repository.BackupCategoryRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@RestController
public class BackupController {

    private final BackupAuthRepository backupAuthRepository;
    private final BackupCategoryRepository backupCategoryRepository;

    public BackupController(BackupAuthRepository backupAuthRepository,
                            BackupCategoryRepository backupCategoryRepository) {
        this.backupAuthRepository = backupAuthRepository;
        this.backupCategoryRepository = backupCategoryRepository;
    }

    // 백업 - 카테고리 리스트 저장
    @PostMapping("/backup")
    @Transactional
    public ResponseEntity<Void> createBackup(@RequestParam String id,
                                             @RequestParam String password,
                                             @RequestBody List<Category> categoryList) {

        try {
            // 사용자 조회 또는 생성
            BackupAuth auth = backupAuthRepository.findById(id).orElse(null);
            if (auth == null) {
                // 새 사용자 생성
                auth = new BackupAuth(id, password);
                backupAuthRepository.save(auth);
            }

            // 기존 백업 데이터 삭제 (해당 사용자의)
            backupCategoryRepository.deleteByBackedUpBy(id);

            // 새로운 백업 데이터 저장
            List<BackupCategory> backupCategories = categoryList.stream()
                    .map(category -> new BackupCategory(category, id))
                    .collect(Collectors.toList());

            backupCategoryRepository.saveAll(backupCategories);

            // 백업 정보 업데이트
            auth.setLastBackupDate(LocalDateTime.now());
            auth.setBackupCount(auth.getBackupCount() + 1);
            backupAuthRepository.save(auth);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // 서버 오류
        }
    }

    // 복원 - 백업된 카테고리 리스트 반환
    @GetMapping("/Restore")
    public ResponseEntity<List<Category>> getRestoreData(@RequestParam String id,
                                                         @RequestParam String password) {

        try {
            // 사용자 조회
            BackupAuth auth = backupAuthRepository.findById(id).orElse(null);
            if (auth == null) {
                // 사용자가 없으면 백업 데이터도 없음
                return ResponseEntity.notFound().build();
            }

            // 해당 사용자의 백업 데이터 조회
            List<BackupCategory> backupCategories = backupCategoryRepository.findByBackedUpByOrderByBackupDateDesc(id);

            // BackupCategory를 Category로 변환
            List<Category> categories = backupCategories.stream()
                    .map(BackupCategory::toCategory)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(categories);

        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // 서버 오류
        }
    }

    // 백업 상태 확인 (추가 기능)
    @GetMapping("/backup/status")
    public ResponseEntity<BackupStatus> getBackupStatus(@RequestParam String id,
                                                        @RequestParam String password) {

        try {
            // 사용자 조회 또는 생성
            BackupAuth auth = backupAuthRepository.findById(id).orElse(null);
            if (auth == null) {
                // 새 사용자 생성
                auth = new BackupAuth(id, password);
                backupAuthRepository.save(auth);
            }

            int categoryCount = backupCategoryRepository.countByBackedUpBy(id);
            BackupStatus status = new BackupStatus(
                    auth.getLastBackupDate(),
                    auth.getBackupCount(),
                    categoryCount
            );

            return ResponseEntity.ok(status);

        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // 서버 오류
        }
    }

    // 백업 상태 응답 클래스
    public static class BackupStatus {
        private LocalDateTime lastBackupDate;
        private int totalBackupCount;
        private int categoryCount;

        public BackupStatus(LocalDateTime lastBackupDate, int totalBackupCount, int categoryCount) {
            this.lastBackupDate = lastBackupDate;
            this.totalBackupCount = totalBackupCount;
            this.categoryCount = categoryCount;
        }

        // Getters
        public LocalDateTime getLastBackupDate() { return lastBackupDate; }
        public int getTotalBackupCount() { return totalBackupCount; }
        public int getCategoryCount() { return categoryCount; }
    }
}
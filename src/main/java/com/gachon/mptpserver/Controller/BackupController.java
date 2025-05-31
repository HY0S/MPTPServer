package com.gachon.mptpserver.Controller;

import com.gachon.mptpserver.DTO.Category;
import com.gachon.mptpserver.Service.BackupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/backup")
public class BackupController {
    private static final Logger logger = LoggerFactory.getLogger(BackupController.class);
    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> backup(
            @RequestParam String id,
            @RequestParam String password,
            @RequestBody List<Category> categories) {
        logger.info("백업 요청: id={}, categories={}", id, categories.size());
        Map<String, String> response = new HashMap<>();

        try {
            // 기존 계정이 있는 경우 비밀번호 검증
            if (backupService.findById(id) != null && !backupService.validatePassword(id, password)) {
                logger.warn("백업 실패: 잘못된 비밀번호 - id={}", id);
                response.put("message", "Invalid password");
                return ResponseEntity.status(409).body(response);
            }

            backupService.backup(id, password, categories);
            logger.info("백업 성공: id={}, categories={}", id, categories.size());
            response.put("message", "Backup successful");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("백업 실패: id={}, error={}", id, e.getMessage());
            response.put("message", "Backup failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/restore")
    public ResponseEntity<?> restore(
            @RequestParam String id,
            @RequestParam String password) {
        logger.info("복원 요청: id={}", id);
        Map<String, Object> response = new HashMap<>();

        try {
            if (!backupService.validatePassword(id, password)) {
                logger.warn("복원 실패: 잘못된 인증 정보 - id={}", id);
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(401).body(response);
            }

            List<Category> categories = backupService.restore(id, password);
            logger.info("복원 성공: id={}, categories={}", id, categories.size());
            return ResponseEntity.ok(categories);

        } catch (Exception e) {
            logger.error("복원 실패: id={}, error={}", id, e.getMessage());
            response.put("message", "Restore failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}

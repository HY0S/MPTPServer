package com.gachon.mptpserver.Service;

import com.gachon.mptpserver.DTO.BackupAuth;
import com.gachon.mptpserver.DTO.BackupCategory;
import com.gachon.mptpserver.DTO.Category;
import com.gachon.mptpserver.Repository.BackupAuthRepository;
import com.gachon.mptpserver.Repository.BackupCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BackupService {
    private final BackupAuthRepository backupAuthRepository;
    private final BackupCategoryRepository backupCategoryRepository;

    public BackupService(BackupAuthRepository backupAuthRepository,
                        BackupCategoryRepository backupCategoryRepository) {
        this.backupAuthRepository = backupAuthRepository;
        this.backupCategoryRepository = backupCategoryRepository;
    }

    @Transactional(readOnly = true)
    public BackupAuth findById(String id) {
        return backupAuthRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean validatePassword(String id, String password) {
        BackupAuth auth = findById(id);
        return auth != null && auth.getPassword().equals(password);
    }

    @Transactional
    public void backup(String id, String password, List<Category> categories) {
        BackupAuth auth = backupAuthRepository.findById(id)
                .orElseGet(() -> {
                    BackupAuth newAuth = new BackupAuth();
                    newAuth.setId(id);
                    newAuth.setPassword(password);
                    return newAuth;
                });

        // 기존 카테고리 삭제
        backupCategoryRepository.deleteAllByBackedUpBy(auth);

        // 새로운 카테고리 저장
        List<BackupCategory> backupCategories = categories.stream()
                .map(category -> {
                    BackupCategory backupCategory = BackupCategory.fromCategory(category);
                    backupCategory.setBackedUpBy(auth);
                    return backupCategory;
                })
                .collect(Collectors.toList());

        auth.incrementBackupCount();
        backupAuthRepository.save(auth);
        backupCategoryRepository.saveAll(backupCategories);
    }

    @Transactional(readOnly = true)
    public List<Category> restore(String id, String password) {
        BackupAuth auth = backupAuthRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Backup not found"));

        if (!auth.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return backupCategoryRepository.findAllByBackedUpBy(auth)
                .stream()
                .map(BackupCategory::toCategory)
                .collect(Collectors.toList());
    }
} 
package com.gachon.mptpserver.Repository;

import com.gachon.mptpserver.DTO.BackupAuth;
import com.gachon.mptpserver.DTO.BackupCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// BackupCategoryRepository.java
@Repository
public interface BackupCategoryRepository extends JpaRepository<BackupCategory, Integer> {

    // 특정 사용자의 백업 카테고리 조회 (최신순)
    List<BackupCategory> findByBackedUpByOrderByBackupDateDesc(BackupAuth backedUpBy);

    // 특정 사용자의 백업 카테고리 개수
    int countByBackedUpBy(BackupAuth backedUpBy);

    // 특정 사용자의 백업 데이터 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM BackupCategory bc WHERE bc.backedUpBy = :backedUpBy")
    void deleteByBackedUpBy(@Param("backedUpBy") String backedUpBy);

    // 특정 날짜 이전의 백업 데이터 삭제 (정리용)
    @Modifying
    @Transactional
    @Query("DELETE FROM BackupCategory bc WHERE bc.backupDate < :date")
    void deleteByBackupDateBefore(@Param("date") java.time.LocalDateTime date);

    List<BackupCategory> findAllByBackedUpBy(BackupAuth backedUpBy);
    void deleteAllByBackedUpBy(BackupAuth backedUpBy);
}
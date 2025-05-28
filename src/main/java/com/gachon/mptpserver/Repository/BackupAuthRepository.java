package com.gachon.mptpserver.Repository;

import com.gachon.mptpserver.DTO.BackupAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// BackupAuthRepository.java
@Repository
public interface BackupAuthRepository extends JpaRepository<BackupAuth, String> {
    // 기본 CRUD는 JpaRepository에서 제공
    // findById, save, delete 등은 자동으로 사용 가능
}
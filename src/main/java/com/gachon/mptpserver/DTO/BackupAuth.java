package com.gachon.mptpserver.DTO;
// BackupAuth.java
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "Backup_Auth")
public class BackupAuth {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "last_backup_date")
    private LocalDateTime lastBackupDate;

    @Column(name = "backup_count")
    private int backupCount = 0;

    // 생성자
    public BackupAuth() {}

    public BackupAuth(String id, String password) {
        this.id = id;
        this.password = password;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getLastBackupDate() { return lastBackupDate; }
    public void setLastBackupDate(LocalDateTime lastBackupDate) { this.lastBackupDate = lastBackupDate; }

    public int getBackupCount() { return backupCount; }
    public void setBackupCount(int backupCount) { this.backupCount = backupCount; }
}


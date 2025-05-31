package com.gachon.mptpserver.DTO;
// BackupAuth.java
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Backup_Auth")
public class BackupAuth {
    @Id
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_backup_date")
    private LocalDateTime lastBackupDate;

    @Column(name = "backup_count")
    private int backupCount;

    @OneToMany(mappedBy = "backedUpBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BackupCategory> categories;

    // 생성자
    public BackupAuth() {
        this.createdDate = LocalDateTime.now();
        this.backupCount = 0;
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

    public List<BackupCategory> getCategories() { return categories; }
    public void setCategories(List<BackupCategory> categories) { this.categories = categories; }

    public void incrementBackupCount() {
        this.backupCount++;
        this.lastBackupDate = LocalDateTime.now();
    }
}


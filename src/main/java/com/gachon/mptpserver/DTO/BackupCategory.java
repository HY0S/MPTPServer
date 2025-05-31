package com.gachon.mptpserver.DTO;

// BackupCategory.java
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Backup_Category")
public class BackupCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "category", length = 255)
    private String category;

    @Column(name = "value")
    private Integer value;

    @Column(name = "date")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backed_up_by", nullable = false)
    private BackupAuth backedUpBy;

    @Column(name = "backup_date")
    private LocalDateTime backupDate;

    // 생성자
    public BackupCategory() {
        this.backupDate = LocalDateTime.now();
    }

    // Category로부터 BackupCategory 생성을 위한 정적 팩토리 메서드
    public static BackupCategory fromCategory(Category category) {
        BackupCategory backupCategory = new BackupCategory();
        backupCategory.setName(category.getName());
        backupCategory.setCategory(category.getCategory());
        backupCategory.setValue(category.getValue());
        backupCategory.setDate(category.getDate());
        return backupCategory;
    }

    // BackupCategory로부터 Category 생성을 위한 메서드
    public Category toCategory() {
        Category category = new Category();
        category.setName(this.name);
        category.setCategory(this.category);
        category.setValue(this.value);
        category.setDate(this.date);
        return category;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public BackupAuth getBackedUpBy() { return backedUpBy; }
    public void setBackedUpBy(BackupAuth backedUpBy) { this.backedUpBy = backedUpBy; }

    public LocalDateTime getBackupDate() { return backupDate; }
    public void setBackupDate(LocalDateTime backupDate) { this.backupDate = backupDate; }
}
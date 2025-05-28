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

    @Column(name = "icon", length = 255)
    private String icon;

    @Column(name = "value")
    private Integer value;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "backed_up_by", nullable = false, length = 100)
    private String backedUpBy;

    @Column(name = "backup_date")
    private LocalDateTime backupDate = LocalDateTime.now();

    // 생성자
    public BackupCategory() {}

    // Category 객체로부터 BackupCategory 생성
    public BackupCategory(Category category, String backedUpBy) {
        this.name = category.getName();
        this.icon = category.getIcon();
        this.value = category.getValue();
        this.date = category.getDate();
        this.backedUpBy = backedUpBy;
    }

    // BackupCategory를 Category로 변환
    public Category toCategory() {
        Category category = new Category();
        category.setName(this.name);
        category.setIcon(this.icon);
        category.setValue(this.value);
        category.setDate(this.date);
        return category;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getBackedUpBy() { return backedUpBy; }
    public void setBackedUpBy(String backedUpBy) { this.backedUpBy = backedUpBy; }

    public LocalDateTime getBackupDate() { return backupDate; }
    public void setBackupDate(LocalDateTime backupDate) { this.backupDate = backupDate; }
}
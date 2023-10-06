package com.namyesol.tuesday.dto.storage;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class StorageObjectForm {
    
    private Long id;
    private Long memberId;
    private String name;
    private String description;
    private List<MultipartFile> files;
    private List<MultipartFile> imageFiles;

    public StorageObjectForm() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<MultipartFile> getFiles() {
        return files;
    }
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
    public List<MultipartFile> getImageFiles() {
        return imageFiles;
    }
    public void setImageFiles(List<MultipartFile> imageFiles) {
        this.imageFiles = imageFiles;
    }

    
}

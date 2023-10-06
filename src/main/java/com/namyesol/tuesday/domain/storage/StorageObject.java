package com.namyesol.tuesday.domain.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StorageObject {
    
    private Long id;
    private Long memberId;
    private String name;
    private String description;
    private List<FileObject> files;
    private List<FileObject> imageFiles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StorageObject() {}

    public StorageObject(Long memberId, String name, String description, List<FileObject> files,
            List<FileObject> imageFiles) {
        this.memberId = memberId;
        this.name = name;
        this.description = description;
        this.files = files;
        this.imageFiles = imageFiles;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
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

    public List<FileObject> getFiles() {
        if (files == null) {
            files = new ArrayList<>();
        }
        return files;
    }

    public void setFiles(List<FileObject> files) {
        this.files = files;
    }

    public List<FileObject> getImageFiles() {
        if (imageFiles == null) {
            imageFiles = new ArrayList<>();
        }
        return imageFiles;
    }

    public void setImageFiles(List<FileObject> imageFiles) {
        this.imageFiles = imageFiles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StorageObject other = (StorageObject) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public void addFile(FileObject file) {
        getFiles().add(file);
    }

    public void addImageFile(FileObject imageFile) {
        getImageFiles().add(imageFile);
    }

}

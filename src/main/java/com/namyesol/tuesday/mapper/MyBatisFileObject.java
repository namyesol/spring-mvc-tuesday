package com.namyesol.tuesday.mapper;

import com.namyesol.tuesday.domain.storage.FileObject;

public class MyBatisFileObject extends FileObject{
    
    private Long id;
    private Long storageId;

    public MyBatisFileObject() {}

    public MyBatisFileObject(Long storageId, String originalFileName, String storeFileName) {
        super(originalFileName, storeFileName);
        this.storageId = storageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

}

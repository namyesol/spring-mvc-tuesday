package com.namyesol.tuesday.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.namyesol.tuesday.domain.storage.FileObject;
import com.namyesol.tuesday.domain.storage.StorageObject;

@Mapper
public interface StorageMapper {
    
    void insert(StorageObject storageObject);

    StorageObject findById(Long id);

    List<StorageObject> findAll();

    void insertFile(MyBatisFileObject fileObject);
    void insertImageFile(MyBatisFileObject fileObject);

    List<FileObject> findFilesByStorageId(Long storageId);
    List<FileObject> findImagesByStorageId(Long storageId);
}

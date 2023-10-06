package com.namyesol.tuesday.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.namyesol.tuesday.domain.storage.FileObject;
import com.namyesol.tuesday.domain.storage.StorageObject;
import com.namyesol.tuesday.mapper.MyBatisFileObject;
import com.namyesol.tuesday.mapper.StorageMapper;

@Repository
public class StorageDao implements StorageMapper{

    private final SqlSessionTemplate sqlSessionTemplate;
	private static final String namespace = "com.namyesol.tuesday.mapper.StorageMapper";

    public StorageDao(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public void insert(StorageObject storageObject) {
        sqlSessionTemplate.insert(namespace + ".insert", storageObject);
        
        List<MyBatisFileObject> files = new ArrayList<>();
        for (FileObject file : storageObject.getFiles()) {
            files.add(new MyBatisFileObject(storageObject.getId(), file.getOriginalFileName(), file.getStoreFileName()));
        } 
        for (MyBatisFileObject file : files) {
            insertFile(file);
        }
        
        List<MyBatisFileObject> imageFiles = new ArrayList<>();
        for (FileObject imageFile : storageObject.getImageFiles()) {
            imageFiles.add(new MyBatisFileObject(storageObject.getId(), imageFile.getOriginalFileName(), imageFile.getStoreFileName()));
        }
        for(MyBatisFileObject imageFile : imageFiles) {
            insertImageFile(imageFile);
        }
      
    }

    @Override
    public List<StorageObject> findAll() {
        List<Long> ids = sqlSessionTemplate.selectList(namespace + ".findIds");
        
        List<StorageObject> result = new ArrayList<>();
        for (Long id : ids) {
            StorageObject storageObject = findById(id);
            result.add(storageObject);
        }

        return result;
    }

    @Override
    public StorageObject findById(Long id) {
        StorageObject storageObject = (StorageObject) sqlSessionTemplate.selectOne(namespace + ".findById", id);
        
        List<FileObject> files = findFilesByStorageId(id);
        for (FileObject file : files) {
            storageObject.addFile(file);
        }
        List<FileObject> imageFiles = findImagesByStorageId(id);
        for (FileObject imageFile : imageFiles) {
            storageObject.addImageFile(imageFile);
        }
        return storageObject;
    }

    @Override
    public List<FileObject> findFilesByStorageId(Long storageId) {
        return sqlSessionTemplate.selectList(namespace + ".findFilesByStorageId", storageId);
    }

    @Override
    public List<FileObject> findImagesByStorageId(Long storageId) {
        return sqlSessionTemplate.selectList(namespace + ".findImagesByStorageId", storageId);
    }

    @Override
    public void insertFile(MyBatisFileObject fileObject) {
       sqlSessionTemplate.insert(namespace + ".insertFile", fileObject);
    }

    @Override
    public void insertImageFile(MyBatisFileObject fileObject) {
        sqlSessionTemplate.insert(namespace + ".insertImageFile", fileObject);
    }
    
}

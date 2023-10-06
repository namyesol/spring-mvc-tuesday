package com.namyesol.tuesday.service.storage;

import java.util.List;

import org.springframework.stereotype.Service;

import com.namyesol.tuesday.dao.StorageDao;
import com.namyesol.tuesday.domain.storage.StorageObject;

@Service
public class StorageService {

    private final StorageDao StorageDao;

    public StorageService(StorageDao storageDao) {
        this.StorageDao = storageDao;
    }

    public void save(StorageObject storageObject) {
        StorageDao.insert(storageObject);
    }

    public StorageObject findById(Long id) {
        return StorageDao.findById(id);
    }

    public List<StorageObject> findAll() {
        return StorageDao.findAll();
    }

}

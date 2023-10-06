package com.namyesol.tuesday.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.namyesol.tuesday.domain.storage.FileObject;
import com.namyesol.tuesday.domain.storage.StorageObject;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/application-context.xml"})
public class StorageMapperIntegrationTest {
    
    @Autowired
    private StorageMapper storageMapper;

    @Test
    public void shouldFindFilesByStorageId() {
        StorageObject storageObject = createStorageObject();
        storageMapper.insert(storageObject);

        List<FileObject> files = storageMapper.findFilesByStorageId(storageObject.getId());
        assertThat(files).hasSize(2);
    }

    @Test
    public void shouldFindImagesByStorageId() {
        StorageObject storageObject = createStorageObject();
        assertThat(storageObject.getImageFiles()).hasSize(3);
        
        storageMapper.insert(storageObject);

        List<FileObject> images = storageMapper.findImagesByStorageId(storageObject.getId());
        assertThat(images).hasSize(3);
       
        FileObject image = storageObject.getImageFiles().get(0);
        FileObject savedImage = images.get(0);

        assertThat(savedImage).hasFieldOrPropertyWithValue("originalFileName", image.getOriginalFileName());
        assertThat(savedImage).hasFieldOrPropertyWithValue("storeFileName", image.getStoreFileName());
    }

    @Test
    public void shouldFindById() {
        StorageObject storageObject = createStorageObject();
        storageMapper.insert(storageObject);

        StorageObject savedStorageObject = storageMapper.findById(storageObject.getId());
       
        List<FileObject> expectedFiles = storageMapper.findFilesByStorageId(storageObject.getId());
        List<FileObject> actualFiles = savedStorageObject.getFiles();

        assertThat(actualFiles).hasSize(expectedFiles.size());
        
    }

    private StorageObject createStorageObject() {
        String text = UUID.randomUUID().toString();
        List<FileObject> files = Arrays.asList(
            new FileObject(text + "-1.txt", text + "-1.txt"),
            new FileObject(text + "-2.txt", text + "-2.txt")
        );

        List<FileObject> images = Arrays.asList(
            new FileObject(text + "-1.png", text + "-1.txt"),
            new FileObject(text + "-2.png", text + "-2.png"),
            new FileObject(text + "-3.png", text + "-3.png")
        );
        StorageObject storageObject = new StorageObject(1L, text, text, files, images);

        return storageObject;
    }
}

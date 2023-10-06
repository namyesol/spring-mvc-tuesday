package com.namyesol.tuesday.service.storage;

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
@ContextConfiguration("file:src/main/webapp/WEB-INF/application-context.xml")
public class StorageServiceIntegrationTest {

    @Autowired
    private StorageService storageService;

    @Test
    public void shouldSaveObject() {
        
        StorageObject storageObject = createStorageObject();
        storageService.save(storageObject);

        assertThat(storageObject.getId()).isNotNull();
    }

    @Test
    public void testFindAll() {
        List<StorageObject> before = storageService.findAll();

        storageService.save(createStorageObject());
        storageService.save(createStorageObject());

        List<StorageObject> after = storageService.findAll();

        assertThat(after).hasSize(before.size() + 2);
        assertThat(after.get(0)).hasNoNullFieldsOrProperties();
    }

    @Test
    public void testFindById() {
        StorageObject storageObject = createStorageObject();
        storageService.save(storageObject);

        StorageObject object = storageService.findById(storageObject.getId());

        assertThat(object).hasNoNullFieldsOrProperties();
        assertThat(object.getFiles()).hasSizeGreaterThan(0);

    }

    private StorageObject createStorageObject() {
        String text = UUID.randomUUID().toString();
        List<FileObject> files = Arrays.asList(
            new FileObject(text + "-1.txt", text + "-1.txt"),
            new FileObject(text + "-2.txt", text + "-2.txt")
        );

        List<FileObject> images = Arrays.asList(
            new FileObject(text + "-1.png", text + "-1.txt"),
            new FileObject(text + "-2.png", text + "-2.png")
        );
        StorageObject storageObject = new StorageObject(1L, text, text, files, images);

        return storageObject;
    }

}

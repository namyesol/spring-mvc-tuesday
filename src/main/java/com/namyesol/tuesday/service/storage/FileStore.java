package com.namyesol.tuesday.service.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.namyesol.tuesday.domain.storage.FileObject;

@Component
public class FileStore {
    
    @Value("${filestore.basedir}")
    private String basedir;

    public String getFullPath(String filename) {
        return basedir + filename;
    }

    public List<FileObject> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<FileObject> result = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                result.add(storeFile(multipartFile));
            }
        }

        return result;
    }

    protected FileObject storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        
        return new FileObject(originalFileName, storeFileName);
    }

    private String createStoreFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExtension(originalFileName);
        return uuid + "." + ext;
    }

    private String extractExtension(String filename) {
        int idx = filename.lastIndexOf(".");
        return filename.substring(idx + 1);
    }
}

package com.namyesol.tuesday.controller.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.namyesol.tuesday.domain.storage.FileObject;
import com.namyesol.tuesday.domain.storage.StorageObject;
import com.namyesol.tuesday.service.storage.FileStore;
import com.namyesol.tuesday.service.storage.StorageService;

@Controller
public class StorageDownloadController {

	private final StorageService storageService;
	private final FileStore fileStore;
	private final Tika tika;

	public StorageDownloadController(StorageService storageService, FileStore fileStore) {
		this.storageService = storageService;
		this.fileStore = fileStore;
		this.tika = new Tika();
	}

	@GetMapping("/images/{filename}")
	public ResponseEntity<Resource> downloadImage(@PathVariable String filename)
		throws MalformedURLException, IOException {
		
		UrlResource resource = new UrlResource(
			"file:" + fileStore.getFullPath(filename)
		);
		
		String contentDisposition = ContentDisposition
				.inline()
				.filename(filename, StandardCharsets.UTF_8)
				.build()
				.toString();
			
		String contentType = tika.detect(resource.getFile());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, contentType)
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(resource);
	}

	@GetMapping("/downloads/{id}/{filename}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id, @PathVariable String filename) 
		throws MalformedURLException {
		
		StorageObject storageObject = storageService.findById(id);
		List<FileObject> files = storageObject.getFiles();
		FileObject file = files.stream().filter(f -> f.getOriginalFileName().equals(filename)).findAny().orElse(null);
		
		if (file == null) {
			return ResponseEntity.notFound().build();
		}

		String originalFileName = file.getOriginalFileName();
		String storeFileName = file.getStoreFileName();
		
		UrlResource resource = new UrlResource(
			"file:" + fileStore.getFullPath(storeFileName)
		);

		String contentDisposition = ContentDisposition
				.attachment()
				.filename(originalFileName, StandardCharsets.UTF_8)
				.build()
				.toString();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(resource);
	}
}

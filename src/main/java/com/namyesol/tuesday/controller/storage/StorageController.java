package com.namyesol.tuesday.controller.storage;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.domain.storage.FileObject;
import com.namyesol.tuesday.domain.storage.StorageObject;
import com.namyesol.tuesday.dto.storage.StorageObjectForm;
import com.namyesol.tuesday.infrastructure.springframework.argumentresolver.CurrentMember;
import com.namyesol.tuesday.service.storage.FileStore;
import com.namyesol.tuesday.service.storage.StorageService;


@Controller
@RequestMapping("/storage")
public class StorageController {
    
    private final StorageService storageService;
	private final FileStore fileStore;
    
    public StorageController(StorageService storageService, FileStore fileStore) {
        this.storageService = storageService;
        this.fileStore = fileStore;
    }

    @GetMapping("/new")
    public String newStorageObjectForm(@ModelAttribute("form") StorageObjectForm form) {
        return "storage/storage-new";
    }

    @PostMapping("/new")
    public String saveStorageObject(@ModelAttribute StorageObjectForm form, BindingResult bindingResult, @CurrentMember Member member, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "storage/storage-new";
        }
        
        List<FileObject> files = fileStore.storeFiles(form.getFiles());
        List<FileObject> images = fileStore.storeFiles(form.getImageFiles());
        StorageObject storageObject = new StorageObject(member.getId(), form.getName(), form.getDescription(), files, images);

        storageService.save(storageObject);

        redirectAttributes.addAttribute("id", storageObject.getId());
        return "redirect:/storage/{id}";
    }

    @GetMapping("/{id}")
    public String storageObjectDetails(@PathVariable Long id, Model model) {
        StorageObject storageObject = storageService.findById(id);
        model.addAttribute("storageObject", storageObject);
        return "storage/storage-details";
    }

    @GetMapping
    public String storageList(Model model) {
        List<StorageObject> storageList = storageService.findAll();
        model.addAttribute("storageList", storageList);
        return "storage/storage-list";
    }
}

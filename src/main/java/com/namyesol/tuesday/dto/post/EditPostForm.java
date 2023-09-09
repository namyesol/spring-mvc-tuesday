package com.namyesol.tuesday.dto.post;

import javax.validation.constraints.NotBlank;

public class EditPostForm {
    
	@NotBlank
    private String title;

    @NotBlank
    private String content;

    public EditPostForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

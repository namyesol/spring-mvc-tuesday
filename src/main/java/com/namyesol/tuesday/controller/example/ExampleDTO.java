package com.namyesol.tuesday.controller.example;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ExampleDTO {
	
	private String uuid;
    
	@NotBlank
	private String author;
	
	@NotNull
	@Size(min = 4, max=25)
	private String title;
	
	@NotBlank
    private String content;
    
    public ExampleDTO() {}
    
	public ExampleDTO(String uuid, String author, String title, String content) {
		this.uuid = uuid;
		this.author = author;
		this.title = title;
		this.content = content;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

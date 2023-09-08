package com.namyesol.tuesday.controller.example;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/example")
public class ExampleController {

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Hello, World!";
	}
	
	@GetMapping("/example-view")
	public String exampleiew() {
		return "example/example-view";
	}
	
	@GetMapping("/example-model")
	public String exampleModel(Model model) {
		model.addAttribute("name", "yesol");
		return "example/example-model";
	}
	
	@GetMapping("/create")
	public String showCreateExampleForm(Model model) {
		model.addAttribute("example", new ExampleDTO());
		return "example/example-create";
	}
	
	@PostMapping("/create")
	public String createExample(@Valid @ModelAttribute("example") ExampleDTO dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "example/example-create";
		}
		
		return "redirect:/example";
	}
	
	@GetMapping("")
	public String showExampleList() {
		return "example/example-list";
	}
}

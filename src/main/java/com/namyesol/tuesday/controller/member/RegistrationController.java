package com.namyesol.tuesday.controller.member;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.dto.member.RegisterForm;
import com.namyesol.tuesday.service.member.RegistrationService;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("form") RegisterForm form) {
        return "register/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegisterForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register/register";
        }

        Member member = new Member(form.getUsername(), form.getPassword(), form.getEmail());
        registrationService.register(member);

        return "redirect:/";
    }
    
}

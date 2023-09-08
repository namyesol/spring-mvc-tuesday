package com.namyesol.tuesday.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.namyesol.tuesday.controller.constant.SessionConstants;
import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.dto.member.LoginForm;
import com.namyesol.tuesday.service.member.LoginService;

@Controller
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
		return "login/login";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
			@RequestParam(name = "redirect", defaultValue = "/") String redirectURL, HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			return "login/login";
		}

		Member member = loginService.login(form.getUsername(), form.getPassword());

		if (member == null) {
			bindingResult.reject("InvalidUsernameOrPassword");
			return "login/login";
		}

		HttpSession session = request.getSession(true);
		session.setAttribute(SessionConstants.AUTHENTICATED_MEMBER, member);
		return "redirect:" + redirectURL;
	}

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/";
	}
}

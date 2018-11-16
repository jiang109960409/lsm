package com.lsm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lsm.domain.UserDO;
import com.lsm.dto.RestRespBody;
import com.lsm.mapper.AppMapper;
import com.lsm.security.JwtManager;

@RestController
public class AppController {

	@Autowired
	private AppMapper appMapper;
	@Autowired
	private JwtManager jwtManager;
	
	@GetMapping("/")
	public ModelAndView index() {
		return new ModelAndView("redirect:login");
	}
	
	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("data", new RestRespBody(true));
		return mav;
	}
	
	@GetMapping("/login")
	public ModelAndView loginPage() {
		return new ModelAndView("login");
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam Map<String, String> params, HttpServletResponse response) {
		String account = params.get("account");
		String password = params.get("password");
		UserDO user = appMapper.getUser(account, password);
		if (user == null) {
			ModelAndView mav = new ModelAndView("login");
			mav.addObject("data", new RestRespBody(false, "账号或密码错误"));
			return mav;
		} else {
			response.setHeader("Authorization", "lsm " + jwtManager.createToken(user.getId(), user.getName()));
			return new ModelAndView("redirect:home");
		}
	}
}

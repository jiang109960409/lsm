package com.lsm.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lsm.anno.PrivilegeId;
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
	
	@PrivilegeId(id="100")
	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("data", new RestRespBody(true));
		return mav;
	}
	
	@GetMapping("/login")
	public ModelAndView loginPage() {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("data", new RestRespBody(true, ""));
		return mav;
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam Map<String, String> params, HttpServletResponse response, HttpSession session) {
		String account = params.get("account");
		String password = params.get("password");
		UserDO user = appMapper.getUser(account, password);
		if (user == null) {
			ModelAndView mav = new ModelAndView("login");
			mav.addObject("data", new RestRespBody(false, "账号或密码错误"));
			return mav;
		} else {
			session.setAttribute("Authorization", "Bearer " + jwtManager.createToken(user.getId(), user.getName(), Arrays.asList(user.getRoleIds().split(",")).stream().map(l -> Integer.parseInt(l)).collect(Collectors.toList())));
			return new ModelAndView("redirect:home");
		}
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.removeAttribute("Authorization");
		return new ModelAndView("redirect:login");
	}
	
	@GetMapping("/401")
	public ModelAndView forbidden() {
		return new ModelAndView("401");
	}
	
}

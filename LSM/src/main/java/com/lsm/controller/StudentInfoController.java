package com.lsm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lsm.dto.RestRespBody;

@RestController
@RequestMapping("/studentinfo")
public class StudentInfoController {

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("data", new RestRespBody(true, "", null, false, "studentinfo"));
		return mav;
	}
}

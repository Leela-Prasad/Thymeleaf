package com.learning.thymeleaf;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping("/")
	public ModelAndView homePage() {
		Map<String,Object> model = new HashMap<>();
		model.put("username", "leela");
		model.put("id", 239);
		return new ModelAndView("homepage",model);
	}
	
	@RequestMapping("/profile")
	public ModelAndView profile() {
		Map<String,Object> model = new HashMap<>();
		model.put("title", "Mr");
		model.put("firstName", "leela");
		model.put("surName", "jagu");
		model.put("dob", new GregorianCalendar(1991, 4, 17));
		model.put("description", "a <strong>fantastic</strong> Java Developer");
		
		List<String> languages = new ArrayList<>();
		languages.add("English");
		languages.add("Telugu");
		languages.add("Spanish");
		languages.add("German");
		
		model.put("languages",languages);
		model.put("color", "#ccc");
		return new ModelAndView("profile","model",model);
	}
	
	@RequestMapping("/profile1")
	public ModelAndView profile1() {
		Map<String,Object> model = new HashMap<>();
		model.put("title", "Mr");
		model.put("firstName", "leela");
		model.put("surName", "jagu");
		model.put("dob", new GregorianCalendar(1991, 4, 17));
		model.put("description", "a <strong>fantastic</strong> Java Developer");
		return new ModelAndView("profile1",model);
	}
}

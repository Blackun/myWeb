package com.blackun.controller;

import com.blackun.domain.SampleDTO;
import com.blackun.domain.SampleVO;
import com.blackun.domain.SampleVOList;
import com.blackun.domain.TodoDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {


	@GetMapping("/doA")
	public void doA(){
		log.info("doA called............");
		log.info("----------------------");
	}

	@GetMapping("/ex01")
	public String ex01(SampleVO vo){
		log.info(""+vo);
		return "ex01";
	}

	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age){
		log.info("name : "+name);
		log.info("age : "+age);
		return "ex02";
	}

	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids){
		log.info("ids : "+ ids);
		return "ex02List";
	}

	@GetMapping("/ex02BeanList")
	public String ex02BeanList(SampleVOList list){
		log.info("list vos : " + list);
		return "ex02BeanList";
	}

	@GetMapping("/ex03")
	public String ex03(TodoDTO todo){
		log.info("todo : " + todo);
		return "ex03";
	}

	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page){
		log.info("dto : " + dto);
		log.info("page : " + page);

		return "/sample/ex04";
	}

	@GetMapping("/ex05")
	public void ex05() {
		log.info("/ex05...........");
	}

	@GetMapping(value = "/ex06", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody SampleDTO ex06(){
		log.info("/ex06..............");

		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("양길완");

		return dto;
	}

	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("/ex07.............");

		// {"name" : "홍길동"}
		String msg = "{\"name\" : \"홍길동\"}";

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
}

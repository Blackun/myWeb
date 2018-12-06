package com.blackun.controller;

import com.blackun.domain.BoardVO;
import com.blackun.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	private BoardService service;

	@GetMapping("/list")
	public void list(){

	}

	@GetMapping("/register")
	public void register(){

	}

	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr){
		log.info("==============================");
		log.info("register: "+board);

		if(board.getAttachList() != null){
			board.getAttachList().forEach(attach -> log.info(attach));
		}

		log.info("===============================");

		service.register(board);
		// rttr.addFlashAttribute("result", board.getBno());

		return "redirect:/board/list";
	}
}

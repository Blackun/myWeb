package com.blackun.controller;

import com.blackun.domain.BoardAttachVO;
import com.blackun.domain.BoardVO;
import com.blackun.service.BoardService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@GetMapping("/get")
	public void get(@RequestParam("bno")Long bno, Model model){
		log.info("/get");
		model.addAttribute("board", service.get(bno));

	}

	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		log.info("getAttachList "+ bno);
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
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

		return "redirect:/board/list";
	}
}

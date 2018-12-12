package com.blackun.controller;

import com.blackun.domain.BoardAttachVO;
import com.blackun.domain.BoardVO;
import com.blackun.domain.Criteria;
import com.blackun.service.BoardService;
import com.blackun.util.MyFiles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	private final String UPLOAD_PATH = "/Users/jgbae/IdeaProjects/myWeb/src/main/webapp/upload/";

	private BoardService service;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		// true passed to CustomDateEditor constructor means convert empty String to null
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@GetMapping("/list")
	public void list(){

	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {

		log.info("/get or modify");
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

	private void deleteFiles(List<BoardAttachVO> attachList){
		if(attachList == null || attachList.size() == 0){
			return;
		}

		log.info("delete attach files...........................");
		log.info(attachList);

		attachList.forEach(attach->{
			try{
				Path file = Paths.get(UPLOAD_PATH + attach.getUploadPath()+"//"+attach.getUuid()+"_"+attach.getFileName());

				if(MyFiles.probeContentType(file).startsWith("image")){
					Path thumbNail = Paths.get(UPLOAD_PATH + attach.getUploadPath()+"/s_"+attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumbNail);
				}

				Files.deleteIfExists(file);
			} catch (Exception e) {
				log.error("delete file error : "+e.getMessage());
			}
		});
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr){
		log.info("remove......" + bno);

		List<BoardAttachVO> attachList = service.getAttachList(bno);
		if(service.remove(bno)){
			//delete Attach Files
			deleteFiles(attachList);

			rttr.addFlashAttribute("result", "succcess");
		}

		return "redirect:/board/list"+cri.getListLink();
	}

	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify:" + board);

		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}

		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());

		return "redirect:/board/list";
	}
}



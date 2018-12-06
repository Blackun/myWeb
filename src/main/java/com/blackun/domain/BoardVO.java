package com.blackun.domain;

import java.util.List;
import java.util.Date;
import lombok.Data;

@Data
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private Date updateDate;

	private int replyCnt;

	private List<BoardAttachVO> attachList;
}

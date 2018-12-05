package com.blackun.domain;

import lombok.Data;

@Data
public class AttachFileDTO {
	private String fileName;
	private String uploadPath;
	private String uuid;
	private boolean image;

	@Data
	public static class BoardAttachVO extends AttachFileDTO{
		private Long bno;
	}
}

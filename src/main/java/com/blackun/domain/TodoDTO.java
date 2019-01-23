package com.blackun.domain;

import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TodoDTO {
	private String title;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date dueDate;
}

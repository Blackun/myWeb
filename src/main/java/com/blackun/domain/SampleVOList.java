package com.blackun.domain;

import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Data
public class SampleVOList {
	private List<SampleVO> list;

	public SampleVOList(){
		list = new ArrayList<>();
	}
}

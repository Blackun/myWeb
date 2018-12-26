package com.blackun.service;

import com.blackun.domain.ReplyVO;
import com.blackun.mapper.BoardMapper;
import com.blackun.mapper.ReplyMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService {

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;

	public int register(ReplyVO vo){
		log.info("register............." + vo);

		boardMapper.updateReplyCnt(vo.getBno(), 1);

		return mapper.insert(vo);
	}
}

package com.blackun.service;

import com.blackun.domain.BoardVO;
import com.blackun.mapper.BoardAttachMapper;
import com.blackun.mapper.BoardMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j
@Service
public class BoardServiceImpl implements BoardService {

	@Setter(onMethod_= @Autowired)
	private BoardMapper mapper;

	@Setter(onMethod_= @Autowired)
	private BoardAttachMapper attachMapper;

	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("register....."+board);

		mapper.insert(board);

		if(board.getAttachList() == null || board.getAttachList().size() <= 0){
			return;
		}

		board.getAttachList().forEach(attach->{
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});

	}
}

package com.blackun.service;

import com.blackun.domain.BoardAttachVO;
import com.blackun.domain.BoardVO;
import com.blackun.mapper.BoardAttachMapper;
import com.blackun.mapper.BoardMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("get Attach list by bno "+ bno);
		return attachMapper.findByBno(bno);
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("get......" + bno);
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public boolean remove(Long bno){
		log.info("remove...." + bno);

		attachMapper.deleteAll(bno);

		return mapper.delete(bno) == 1;
	}

	@Transactional
	@Override
	public boolean modify(BoardVO board) {

		log.info("modify......" + board);

		attachMapper.deleteAll(board.getBno());

		boolean modifyResult = mapper.update(board) == 1;

		if (modifyResult && Optional.ofNullable(board.getAttachList()).orElse(Collections.emptyList()).size() > 0) {

			board.getAttachList().forEach(attach -> {

				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}

		return modifyResult;
	}
}

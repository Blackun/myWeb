package com.blackun.service;

import com.blackun.domain.BoardAttachVO;
import com.blackun.domain.BoardVO;
import java.util.List;

public interface BoardService {
	void register(BoardVO board);
	List<BoardAttachVO> getAttachList(Long bno);
	BoardVO get(Long bno);
	boolean remove(Long bno);

	boolean modify(BoardVO board);
}

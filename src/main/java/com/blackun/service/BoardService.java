package com.blackun.service;

import com.blackun.domain.BoardAttachVO;
import com.blackun.domain.BoardVO;
import java.util.List;

public interface BoardService {
	public void register(BoardVO board);

	public List<BoardAttachVO> getAttachList(Long bno);

	BoardVO get(Long bno);
}

package com.blackun.mapper;

import com.blackun.domain.BoardVO;
import java.util.List;


public interface BoardMapper {
	public List<BoardVO> getList();
	public int insert(BoardVO board);
	public BoardVO read(Long bno);
}

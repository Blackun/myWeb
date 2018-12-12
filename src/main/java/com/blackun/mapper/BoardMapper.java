package com.blackun.mapper;

import com.blackun.domain.BoardVO;
import java.util.List;


public interface BoardMapper {
	List<BoardVO> getList();
	int insert(BoardVO board);
	BoardVO read(Long bno);
	int delete(Long bno);
	int update(BoardVO board);

}

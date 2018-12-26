package com.blackun.mapper;

import com.blackun.domain.BoardVO;
import com.blackun.domain.Criteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardMapper {
	List<BoardVO> getList();
	List<BoardVO> getListWithPaging(Criteria cri);
	int insert(BoardVO board);
	BoardVO read(Long bno);
	int delete(Long bno);
	int update(BoardVO board);

	void updateReplyCnt(@Param("bno") Long bno, @Param("amount")int amount);

}

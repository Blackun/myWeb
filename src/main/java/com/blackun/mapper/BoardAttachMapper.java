package com.blackun.mapper;

import com.blackun.domain.AttachFileDTO;
import java.util.List;

public interface BoardAttachMapper {
	public void insert(AttachFileDTO.BoardAttachVO vo);
	public void delete(String uuid);
	public List<AttachFileDTO.BoardAttachVO> findByBno(Long bno);
}

package com.blackun.mapper;

import com.blackun.domain.ReplyVO;
import java.util.stream.IntStream;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:/spring/application*")
@Log4j
public class ReplyMapperTests {

	private Long[] bnoArr = {3145745L, 3145744L, 3145743L, 3145742L, 3145741L};

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	@Test
	public void testMapper(){
		log.info(mapper);
	}

	@Test
	public void testCreate(){
		IntStream.rangeClosed(1, 10).forEach(i->{
			ReplyVO vo = new ReplyVO(){{
				setBno(bnoArr[i%5]);
				setReply("댓글 테스트 "+i);
				setReplyer("replyer"+i);
			}};
			mapper.insert(vo);
		});
	}

	@Test
	public void testRead(){
		Long targetRno = 5L;
		ReplyVO vo = mapper.read(targetRno);
		log.info(vo);
	}

	@Test
	public void testDelete(){
		Long targetRno = 1L;
		mapper.delete(targetRno);
	}

	@Test
	public void testUpdate(){
		Long targetRno = 10L;
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("Update Reply ");
		int count = mapper.update(vo);
		log.info("UPDATE COUNT : " + count);
	}
}

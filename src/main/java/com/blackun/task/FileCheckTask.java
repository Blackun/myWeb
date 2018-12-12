package com.blackun.task;

import com.blackun.domain.BoardAttachVO;
import com.blackun.mapper.BoardAttachMapper;
import com.blackun.util.Streams;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class FileCheckTask {

	@Value("${myweb.uploadpath}")
	private String UPLOAD_PATH;

	@Setter(onMethod_ = {@Autowired })
	private BoardAttachMapper attachMapper;

	@Scheduled(cron="0 0 2 * * *")
	public void checkFiles() throws Exception{
		log.warn("File check Task run................");
		log.warn(new Date());

		// file list in database
		List<BoardAttachVO> fileList =attachMapper.getOldFiles();

		// ready for check file in directory with database file list
		List<Path> fileListPaths = Streams.ofNullable(fileList)
										.map(vo->Paths.get(UPLOAD_PATH, vo.getUploadPath(), vo.getUuid() +"_"+vo.getFileName()))
										.collect(Collectors.toList());

		// image file has thumbnail file
		Streams.ofNullable(fileList)
				.filter(vo-> vo.isFileType() == true)
				.map(vo->Paths.get(UPLOAD_PATH, vo.getUploadPath(), "s_"+vo.getUuid()+"_"+vo.getFileName()))
				.forEach(p->fileListPaths.add(p));

		log.warn("===============================================");

		fileListPaths.forEach(p->log.warn(p));

		// files in yesterday directory
		File targetDir = Paths.get(UPLOAD_PATH, getFolderYesterDay()).toFile();

		File[] removeFiles = targetDir.listFiles(file->fileListPaths.contains(file.toPath()) == false);

		log.warn("-----------------------------------------------");
		for(File file:removeFiles){
			log.warn(file.getAbsolutePath());
			file.delete();
		}
	}

	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String str = sdf.format(cal.getTime());
		return str.replace("-", File.separator);
	}
}

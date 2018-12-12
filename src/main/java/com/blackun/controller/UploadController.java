package com.blackun.controller;

import com.blackun.domain.AttachFileDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Log4j
public class UploadController extends MywebController{

	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}

	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		String uploadFolder = UPLOAD_PATH;

		for (MultipartFile multipartFile : uploadFile) {
			log.info("---------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());

			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}

	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}

	private boolean checkImageType(File file){
		Magic magic = new Magic();
		try {
			MagicMatch match = magic.getMagicMatch(file, false);
			return match.getMimeType().contains("image");
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@PostMapping(value="/uploadAjaxAction", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		List<AttachFileDTO> list = new ArrayList<>();

		String uploadFolder = UPLOAD_PATH;

		String uploadFolderPath = getFolder();
		// make folder --------
		File uploadPath = new File(uploadFolder, uploadFolderPath);

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		// make yyyy/MM/dd folder

		for (MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();

			String uploadFileName = multipartFile.getOriginalFilename();

			// IE 에서는 file path 를 가지고 있으므로 대응하는 코드 작성
			if (uploadFileName.contains("\\"))
				uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\" + 1));
			log.info("only file name: " + uploadFileName);
			attachDTO.setFileName(uploadFileName);

			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;

			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);

				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);

				// 이미지 파일이면 썸네일 만들기
				if(checkImageType(saveFile)){

					attachDTO.setImage(true);

					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}

				// add to List
				list.add(attachDTO);

			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName: " + fileName);

		File file = new File(UPLOAD_PATH + fileName);
		log.info("file: "+file);

		ResponseEntity<byte[]> result = null;

		try{
			HttpHeaders header = new HttpHeaders();

			Magic magic = new Magic();
			MagicMatch magicMatch = magic.getMagicMatch(file, false);
			header.add("Content-Type",  magicMatch.getMimeType());
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch(IOException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}

		return result;
	}

	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile: "+ fileName);

		File file;
		try{
			file = new File(UPLOAD_PATH+ URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			if(type.equals("image")){
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				log.info("largeFileName: "+largeFileName);

				file = new File(largeFileName);
				file.delete();
			}
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>("delete", HttpStatus.OK);
	}

	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent")String userAgent, String fileName){
		Resource resource = new FileSystemResource(UPLOAD_PATH + fileName);

		if(resource.exists() == false){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String resourceName = resource.getFilename();

		// remove UUID
		String resourcOriginalName = resourceName.substring(resourceName.indexOf("_")+1);

		HttpHeaders headers = new HttpHeaders();
		try{
			String downloadName = null;

			if(userAgent.contains("Trident")){
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourcOriginalName, "UTF-8").replaceAll("\\+", " ");
			} else if(userAgent.contains("Edge")){
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourcOriginalName, "UTF-8");
			} else {
				log.info("Chrome browser");
				downloadName = new String(resourcOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}

			log.info("downloadName: "+downloadName);

			headers.add("Content-Disposition", "attachment; filename="+downloadName);
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
}



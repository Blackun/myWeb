
-- 게시물 저장
CREATE TABLE `tbl_board` (
  `bno` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `writer` varchar(50) NOT NULL,
  `regDate` datetime(0) DEFAULT NULL,
  `updateDate` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`bno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


-- 첨부파일 정보를 저장
CREATE TABLE `myweb`.`tbl_attach` (
  `uuid` VARCHAR(100) NOT NULL,
  `uploadPath` VARCHAR(200) NOT NULL,
  `fileName` VARCHAR(100) NOT NULL,
  `fileType` CHAR(1) NULL DEFAULT '1',
  `bno` BIGINT(10) NULL,
  PRIMARY KEY (`uuid`));

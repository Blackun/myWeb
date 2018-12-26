-- transaction 테스트 테이블
CREATE TABLE `myweb`.`tbl_sample1` (
  `col1` VARCHAR(500) NULL);

CREATE TABLE `myweb`.`tbl_sample2` (
  `col2` VARCHAR(50) NULL);


-- 게시물 저장
CREATE TABLE `myweb`.`tbl_board` (
  `bno` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `writer` varchar(50) NOT NULL,
  `regDate` datetime(0) DEFAULT NULL,
  `updateDate` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`bno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 게시물 저장 테이블 수정
ALTER TABLE `myweb`.`tbl_board`
ADD COLUMN `replyCnt` INT NOT NULL DEFAULT 0 AFTER `updateDate`;

-- 댓글 저장
CREATE TABLE `tbl_reply` (
  `rno` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `bno` bigint(10) NOT NULL,
  `reply` varchar(200) NOT NULL,
  `replyer` varchar(45) NOT NULL,
  `replyDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`rno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 첨부파일 정보를 저장
CREATE TABLE `myweb`.`tbl_attach` (
  `uuid` VARCHAR(100) NOT NULL,
  `uploadPath` VARCHAR(200) NOT NULL,
  `fileName` VARCHAR(100) NOT NULL,
  `fileType` CHAR(1) NULL DEFAULT '1',
  `bno` BIGINT(10) NULL,
  PRIMARY KEY (`uuid`));

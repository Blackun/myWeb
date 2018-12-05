-- 첨부파일 정보를 저장
CREATE TABLE `myweb`.`new_table` (
  `uuid` VARCHAR(100) NOT NULL,
  `uploadPath` VARCHAR(200) NOT NULL,
  `fileName` VARCHAR(100) NOT NULL,
  `fileType` CHAR(1) NULL DEFAULT 'I',
  `bno` BIGINT(10) NULL,
  PRIMARY KEY (`uuid`));

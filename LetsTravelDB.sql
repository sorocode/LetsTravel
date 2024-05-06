-- DB 접속
-- mysql -u [dbAccount_id] -p

-- 현재 접속한 DB 계정 확인
SELECT user();

-- DB를 로컬에서 접속할 계정 만들기
CREATE USER 'travel_admin'@'localhost' IDENTIFIED BY 'mysql';

-- DB를 원격에서 접속할 계정 만들기
CREATE USER 'travel_admin'@'IP' IDENTIFIED BY 'mysql';

-- DB 권한 부여
GRANT ALL PRIVILEGES ON LETSTRAVEL.* TO 'travel';

-- DB 계정 권한 확인
SHOW GRANTS FOR 'travel_admin'@'localhost';

-- DB 계정 삭제
DROP USER 'travel_admin'@'localhost';

-- DB 제거
DROP DATABASE LETSTRAVEL;

-- 모든 DB 조회
SHOW DATABASES;

-- DB 선택
USE DATABASE LETSTRAVEL;

-- DB 내의 TABLE 조회
SHOW TABLES;


-- 이 아래부터 다 실행시키면 됨

-- DB 생성 + 기본 인코딩: UTF-8
CREATE DATABASE LETSTRAVEL DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- DB 선택
USE DATABASE LETSTRAVEL;
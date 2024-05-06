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

-- User 테이블
CREATE TABLE User(
	User_seq INT PRIMARY KEY AUTO_INCREMENT,
	Email VARCHAR(60) NOT NULL UNIQUE,
	Password VARCHAR(20) NOT NULL,
	Fname VARCHAR(15) NOT NULL,
	Lname VARCHAR(15) NOT NULL,
	NickName VARCHAR(15) UNIQUE,
	Birthdate DATE NOT NULL,
	Sex TINYINT(1) NOT NULL,
	Regtime DATE NOT NULL DEFAULT (CURRENT_DATE),
	Logintime TIMESTAMP NOT NULL CURRENT_TIMESTAMP,
	User_status TINYINT(1) NOT NULL
);

CREATE TABLE Country(
	Country_code CHAR(2) PRIMARY KEY,
	Country_name VARCHAR(?) NOT NULL UNIQUE,
	Country_name_translated VARCHAR(?) NOT NULL UNIQUE
);

CREATE TABLE City(
	City_seq SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	Country_code CHAR(2) NOT NULL,
	City_name VARCHAR(?) NOT NULL,
	City_name_translated VARCHAR(?) NOT NULL,
	FOREIGN KEY(Country_code) REFERENCES Country(Country_code)
	UNIQUE(Country_code, City_name),
);

CREATE TABLE Place(
	Place_seq INT PRIMARY KEY AUTO_INCREMENT,
	Place_id VARCHAR(255) NOT NULL UNIQUE,
	Place_name VARCHAR(60) NOT NULL,
	Place_formatted_address VARCHAR(200) UNIQUE,
	City_seq SMALLINT UNSIGNED NOT NULL,
	Place_latitude FLOAT NOT NULL,
	Place_longitude FLOAT NOT NULL,
	Place_gmap_uri VARCHAR(500) NOT NULL UNIQUE
	Place_insert_date DATE NOT NULL,
	FOREIGN KEY(City_seq) REFERENCES City(City_seq),
	UNIQUE(Place_latitude, Place_longitude)
);
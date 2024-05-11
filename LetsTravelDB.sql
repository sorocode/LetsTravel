-- DB 접속
-- mysql -u [dbAccount_id] -p

-- 현재 접속한 DB 계정 확인
SELECT user();

-- DB를 로컬에서 접속할 계정 만들기
CREATE USER 'testid'@'localhost' IDENTIFIED BY 'testpw';

-- DB를 원격에서 접속할 계정 만들기
CREATE USER 'travel_admin'@'IP' IDENTIFIED BY 'mysql';

-- DB 권한 부여
GRANT ALL PRIVILEGES ON TESTDB.* TO 'testid'@'localhost';

-- DB 계정 권한 확인
SHOW GRANTS FOR 'testid'@'localhost';

-- DB 계정 삭제
DROP USER 'testid'@'localhost';

-- DB 제거
DROP DATABASE LETSTRAVEL;

-- 모든 DB 조회
SHOW DATABASES;

-- DB 선택
USE LETSTRAVEL;

-- DB 내의 TABLE 조회
SHOW TABLES;


-- 이 아래부터 다 실행시키면 됨

-- DB 생성 + 기본 인코딩: UTF-8
CREATE DATABASE TESTDB DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- DB 선택
USE TESTDB;

-- Table 생성
CREATE TABLE Member(
	Mem_seq INT PRIMARY KEY AUTO_INCREMENT,
	Email VARCHAR(60) NOT NULL UNIQUE,
	Password VARCHAR(20) NOT NULL,
	Fname VARCHAR(15) NOT NULL,
	Lname VARCHAR(15) NOT NULL,
	NickName VARCHAR(15) UNIQUE,
	Birthdate DATE NOT NULL,
	Sex TINYINT(1) NOT NULL,
	Regtime DATE NOT NULL DEFAULT (CURRENT_DATE),
	Logintime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	Mem_status TINYINT(1) NOT NULL
);

CREATE TABLE Country(
	Country_code CHAR(2) PRIMARY KEY,
	Country_name VARCHAR(17) NOT NULL UNIQUE,
	Is_travel_ban BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE City(
	City_seq SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	Country_code CHAR(2) NOT NULL,
	City_name VARCHAR(85) NOT NULL,
	City_name_translated VARCHAR(30) NOT NULL,
	Is_travel_ban BOOLEAN NOT NULL DEFAULT FALSE,
	FOREIGN KEY(Country_code) REFERENCES Country(Country_code),
	UNIQUE(Country_code, City_name)
);

CREATE TABLE Place(
	Place_seq INT PRIMARY KEY AUTO_INCREMENT,
	Place_id VARCHAR(255) NOT NULL UNIQUE,
	Place_name VARCHAR(60) NOT NULL,
	Place_formatted_address VARCHAR(200) UNIQUE,
	City_seq SMALLINT UNSIGNED NOT NULL,
	Place_latitude FLOAT NOT NULL,
	Place_longitude FLOAT NOT NULL,
	Place_gmap_uri VARCHAR(500) NOT NULL UNIQUE,
	Place_insert_date DATE NOT NULL,
	FOREIGN KEY(City_seq) REFERENCES City(City_seq),
	UNIQUE(Place_latitude, Place_longitude)
);

CREATE TABLE Type(
	Type_seq TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	Type_name VARCHAR(35) NOT NULL UNIQUE,
	Type_name_translated VARCHAR(20) UNIQUE
);

CREATE TABLE Place_type(
	Place_seq INT NOT NULL,
	Type_seq TINYINT UNSIGNED NOT NULL,
	Is_primary_type BOOLEAN NOT NULL,
	FOREIGN KEY(Place_seq) REFERENCES Place(Place_seq),
	FOREIGN KEY(Type_seq) REFERENCES Type(Type_seq),
	PRIMARY KEY(Place_seq, Type_seq)
);

CREATE TABLE Plan(
	Plan_seq INT PRIMARY KEY AUTO_INCREMENT,
	Mem_seq INT NOT NULL,
	Plan_name VARCHAR(15),
	Country_code CHAR(2) NOT NULL,
	Plan_start DATE NOT NULL,
	Plan_ndays TINYINT NOT NULL,
	FOREIGN KEY(Mem_seq) REFERENCES Member(Mem_seq),
	FOREIGN KEY(Country_code) REFERENCES Country(Country_code),
	UNIQUE(Mem_seq, Plan_name)
);

CREATE TABLE Plan_city(
	Plan_seq INT NOT NULL,
	City_seq SMALLINT UNSIGNED NOT NULL,
	FOREIGN KEY(Plan_seq) REFERENCES Plan(Plan_seq),
	FOREIGN KEY(City_seq) REFERENCES City(City_seq),
	PRIMARY KEY(Plan_seq, City_seq)
);

CREATE TABLE Plan_share(
	Plan_seq INT NOT NULL,
	Mem_seq INT NOT NULL,
	FOREIGN KEY(Plan_seq) REFERENCES Plan(Plan_seq),
	FOREIGN KEY(Mem_seq) REFERENCES Member(Mem_seq),
	PRIMARY KEY(Plan_seq, Mem_seq)
);

CREATE TABLE Schedule(
	Schedule_seq INT PRIMARY KEY AUTO_INCREMENT,
	Plan_seq INT NOT NULL,
	Place_seq INT NOT NULL,
	Date_seq TINYINT,
	Visit_seq TINYINT UNSIGNED,
	Visit_time TIME,
	FOREIGN KEY(Plan_seq) REFERENCES Plan(Plan_seq),
	FOREIGN KEY(Place_seq) REFERENCES Place(Place_seq),
	UNIQUE(Plan_seq, Place_seq),
	UNIQUE(Plan_seq, Date_seq, Visit_seq)
);

-- Country Records
INSERT INTO Country(Country_code, Country_name) VALUES('AD', '안도라');
INSERT INTO Country(Country_code, Country_name) VALUES('AE', '아랍에미리트');
INSERT INTO Country(Country_code, Country_name) VALUES('AF', '아프가니스탄');
INSERT INTO Country(Country_code, Country_name) VALUES('AG', '앤티가 바부다');
INSERT INTO Country(Country_code, Country_name) VALUES('AI', '앵귈라');
INSERT INTO Country(Country_code, Country_name) VALUES('AL', '알바니아');
INSERT INTO Country(Country_code, Country_name) VALUES('AM', '아르메니아');
INSERT INTO Country(Country_code, Country_name) VALUES('AO', '앙골라');
INSERT INTO Country(Country_code, Country_name) VALUES('AQ', '남극');
INSERT INTO Country(Country_code, Country_name) VALUES('AR', '아르헨티나');
INSERT INTO Country(Country_code, Country_name) VALUES('AS', '아메리칸사모아');
INSERT INTO Country(Country_code, Country_name) VALUES('AT', '오스트리아');
INSERT INTO Country(Country_code, Country_name) VALUES('AU', '오스트레일리아');
INSERT INTO Country(Country_code, Country_name) VALUES('AW', '아루바');
INSERT INTO Country(Country_code, Country_name) VALUES('AX', '올란드 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('AZ', '아제르바이잔');
INSERT INTO Country(Country_code, Country_name) VALUES('BA', '보스니아 헤르체고비나');
INSERT INTO Country(Country_code, Country_name) VALUES('BB', '바베이도스');
INSERT INTO Country(Country_code, Country_name) VALUES('BD', '방글라데시');
INSERT INTO Country(Country_code, Country_name) VALUES('BE', '벨기에');
INSERT INTO Country(Country_code, Country_name) VALUES('BF', '부르키나파소');
INSERT INTO Country(Country_code, Country_name) VALUES('BG', '불가리아');
INSERT INTO Country(Country_code, Country_name) VALUES('BH', '바레인');
INSERT INTO Country(Country_code, Country_name) VALUES('BI', '부룬디');
INSERT INTO Country(Country_code, Country_name) VALUES('BJ', '베냉');
INSERT INTO Country(Country_code, Country_name) VALUES('BL', '생바르텔레미');
INSERT INTO Country(Country_code, Country_name) VALUES('BM', '버뮤다');
INSERT INTO Country(Country_code, Country_name) VALUES('BN', '브루나이');
INSERT INTO Country(Country_code, Country_name) VALUES('BO', '볼리비아');
INSERT INTO Country(Country_code, Country_name) VALUES('BQ', '보네르섬');
INSERT INTO Country(Country_code, Country_name) VALUES('BR', '브라질');
INSERT INTO Country(Country_code, Country_name) VALUES('BS', '바하마');
INSERT INTO Country(Country_code, Country_name) VALUES('BT', '부탄');
INSERT INTO Country(Country_code, Country_name) VALUES('BV', '부베섬');
INSERT INTO Country(Country_code, Country_name) VALUES('BW', '보츠와나');
INSERT INTO Country(Country_code, Country_name) VALUES('BY', '벨라루스');
INSERT INTO Country(Country_code, Country_name) VALUES('BZ', '벨리즈');
INSERT INTO Country(Country_code, Country_name) VALUES('CA', '캐나다');
INSERT INTO Country(Country_code, Country_name) VALUES('CC', '코코스 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('CD', '콩고 민주 공화국');
INSERT INTO Country(Country_code, Country_name) VALUES('CF', '중앙아프리카 공화국');
INSERT INTO Country(Country_code, Country_name) VALUES('CG', '콩고 공화국');
INSERT INTO Country(Country_code, Country_name) VALUES('CH', '스위스');
INSERT INTO Country(Country_code, Country_name) VALUES('CI', '코트디부아르');
INSERT INTO Country(Country_code, Country_name) VALUES('CK', '쿡 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('CL', '칠레');
INSERT INTO Country(Country_code, Country_name) VALUES('CM', '카메룬');
INSERT INTO Country(Country_code, Country_name) VALUES('CN', '중국');
INSERT INTO Country(Country_code, Country_name) VALUES('CO', '콜롬비아');
INSERT INTO Country(Country_code, Country_name) VALUES('CR', '코스타리카');
INSERT INTO Country(Country_code, Country_name) VALUES('CU', '쿠바');
INSERT INTO Country(Country_code, Country_name) VALUES('CV', '카보베르데');
INSERT INTO Country(Country_code, Country_name) VALUES('CW', '퀴라소');
INSERT INTO Country(Country_code, Country_name) VALUES('CX', '크리스마스섬');
INSERT INTO Country(Country_code, Country_name) VALUES('CY', '키프로스');
INSERT INTO Country(Country_code, Country_name) VALUES('CZ', '체코');
INSERT INTO Country(Country_code, Country_name) VALUES('DE', '독일');
INSERT INTO Country(Country_code, Country_name) VALUES('DJ', '지부티');
INSERT INTO Country(Country_code, Country_name) VALUES('DK', '덴마크');
INSERT INTO Country(Country_code, Country_name) VALUES('DM', '도미니카 연방');
INSERT INTO Country(Country_code, Country_name) VALUES('DO', '도미니카 공화국');
INSERT INTO Country(Country_code, Country_name) VALUES('DZ', '알제리');
INSERT INTO Country(Country_code, Country_name) VALUES('EC', '에콰도르');
INSERT INTO Country(Country_code, Country_name) VALUES('EE', '에스토니아');
INSERT INTO Country(Country_code, Country_name) VALUES('EG', '이집트');
INSERT INTO Country(Country_code, Country_name) VALUES('EH', '서사하라');
INSERT INTO Country(Country_code, Country_name) VALUES('ER', '에리트레아');
INSERT INTO Country(Country_code, Country_name) VALUES('ES', '스페인');
INSERT INTO Country(Country_code, Country_name) VALUES('ET', '에티오피아');
INSERT INTO Country(Country_code, Country_name) VALUES('FI', '핀란드');
INSERT INTO Country(Country_code, Country_name) VALUES('FJ', '피지');
INSERT INTO Country(Country_code, Country_name) VALUES('FK', '포클랜드 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('FM', '미크로네시아 연방');
INSERT INTO Country(Country_code, Country_name) VALUES('FO', '페로 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('FR', '프랑스');
INSERT INTO Country(Country_code, Country_name) VALUES('GA', '가봉');
INSERT INTO Country(Country_code, Country_name) VALUES('GB', '영국');
INSERT INTO Country(Country_code, Country_name) VALUES('GD', '그레나다');
INSERT INTO Country(Country_code, Country_name) VALUES('GE', '조지아');
INSERT INTO Country(Country_code, Country_name) VALUES('GF', '기아나');
INSERT INTO Country(Country_code, Country_name) VALUES('GG', '건지섬');
INSERT INTO Country(Country_code, Country_name) VALUES('GH', '가나');
INSERT INTO Country(Country_code, Country_name) VALUES('GI', '지브롤터');
INSERT INTO Country(Country_code, Country_name) VALUES('GL', '그린란드');
INSERT INTO Country(Country_code, Country_name) VALUES('GM', '감비아');
INSERT INTO Country(Country_code, Country_name) VALUES('GN', '기니');
INSERT INTO Country(Country_code, Country_name) VALUES('GP', '과들루프');
INSERT INTO Country(Country_code, Country_name) VALUES('GQ', '적도 기니');
INSERT INTO Country(Country_code, Country_name) VALUES('GR', '그리스');
INSERT INTO Country(Country_code, Country_name) VALUES('GS', '사우스조지아 사우스샌드위치 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('GT', '과테말라');
INSERT INTO Country(Country_code, Country_name) VALUES('GU', '괌');
INSERT INTO Country(Country_code, Country_name) VALUES('GW', '기니비사우');
INSERT INTO Country(Country_code, Country_name) VALUES('GY', '가이아나');
INSERT INTO Country(Country_code, Country_name) VALUES('HK', '홍콩');
INSERT INTO Country(Country_code, Country_name) VALUES('HM', '허드 맥도널드 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('HN', '온두라스');
INSERT INTO Country(Country_code, Country_name) VALUES('HR', '크로아티아');
INSERT INTO Country(Country_code, Country_name) VALUES('HT', '아이티');
INSERT INTO Country(Country_code, Country_name) VALUES('HU', '헝가리');
INSERT INTO Country(Country_code, Country_name) VALUES('ID', '인도네시아');
INSERT INTO Country(Country_code, Country_name) VALUES('IE', '아일랜드');
INSERT INTO Country(Country_code, Country_name) VALUES('IL', '이스라엘');
INSERT INTO Country(Country_code, Country_name) VALUES('IM', '맨섬');
INSERT INTO Country(Country_code, Country_name) VALUES('IN', '인도');
INSERT INTO Country(Country_code, Country_name) VALUES('IO', '영국령 인도양 지역');
INSERT INTO Country(Country_code, Country_name) VALUES('IQ', '이라크');
INSERT INTO Country(Country_code, Country_name) VALUES('IR', '이란');
INSERT INTO Country(Country_code, Country_name) VALUES('IS', '아이슬란드');
INSERT INTO Country(Country_code, Country_name) VALUES('IT', '이탈리아');
INSERT INTO Country(Country_code, Country_name) VALUES('JE', '저지섬');
INSERT INTO Country(Country_code, Country_name) VALUES('JM', '자메이카');
INSERT INTO Country(Country_code, Country_name) VALUES('JO', '요르단');
INSERT INTO Country(Country_code, Country_name) VALUES('JP', '일본');
INSERT INTO Country(Country_code, Country_name) VALUES('KE', '케냐');
INSERT INTO Country(Country_code, Country_name) VALUES('KG', '키르기스스탄');
INSERT INTO Country(Country_code, Country_name) VALUES('KH', '캄보디아');
INSERT INTO Country(Country_code, Country_name) VALUES('KI', '키리바시');
INSERT INTO Country(Country_code, Country_name) VALUES('KM', '코모로');
INSERT INTO Country(Country_code, Country_name) VALUES('KN', '세인트키츠 네비스');
INSERT INTO Country(Country_code, Country_name) VALUES('KP', '조선민주주의인민공화국');
INSERT INTO Country(Country_code, Country_name) VALUES('KR', '대한민국');
INSERT INTO Country(Country_code, Country_name) VALUES('KW', '쿠웨이트');
INSERT INTO Country(Country_code, Country_name) VALUES('KY', '케이맨 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('KZ', '카자흐스탄');
INSERT INTO Country(Country_code, Country_name) VALUES('LA', '라오스');
INSERT INTO Country(Country_code, Country_name) VALUES('LB', '레바논');
INSERT INTO Country(Country_code, Country_name) VALUES('LC', '세인트루시아');
INSERT INTO Country(Country_code, Country_name) VALUES('LK', '스리랑카');
INSERT INTO Country(Country_code, Country_name) VALUES('LI', '리히텐슈타인');
INSERT INTO Country(Country_code, Country_name) VALUES('LR', '라이베리아');
INSERT INTO Country(Country_code, Country_name) VALUES('LS', '레소토');
INSERT INTO Country(Country_code, Country_name) VALUES('LT', '리투아니아');
INSERT INTO Country(Country_code, Country_name) VALUES('LU', '룩셈부르크');
INSERT INTO Country(Country_code, Country_name) VALUES('LV', '라트비아');
INSERT INTO Country(Country_code, Country_name) VALUES('LY', '리비아');
INSERT INTO Country(Country_code, Country_name) VALUES('MA', '모로코');
INSERT INTO Country(Country_code, Country_name) VALUES('MC', '모나코');
INSERT INTO Country(Country_code, Country_name) VALUES('MD', '몰도바');
INSERT INTO Country(Country_code, Country_name) VALUES('ME', '몬테네그로');
INSERT INTO Country(Country_code, Country_name) VALUES('MF', '생마르탱');
INSERT INTO Country(Country_code, Country_name) VALUES('MG', '마다가스카르');
INSERT INTO Country(Country_code, Country_name) VALUES('MH', '마셜 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('MK', '북마케도니아');
INSERT INTO Country(Country_code, Country_name) VALUES('ML', '말리');
INSERT INTO Country(Country_code, Country_name) VALUES('MM', '미얀마');
INSERT INTO Country(Country_code, Country_name) VALUES('MN', '몽골');
INSERT INTO Country(Country_code, Country_name) VALUES('MO', '마카오');
INSERT INTO Country(Country_code, Country_name) VALUES('MP', '북마리아나 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('MQ', '마르티니크');
INSERT INTO Country(Country_code, Country_name) VALUES('MR', '모리타니');
INSERT INTO Country(Country_code, Country_name) VALUES('MS', '몬트세랫');
INSERT INTO Country(Country_code, Country_name) VALUES('MT', '몰타');
INSERT INTO Country(Country_code, Country_name) VALUES('MU', '모리셔스');
INSERT INTO Country(Country_code, Country_name) VALUES('MV', '몰디브');
INSERT INTO Country(Country_code, Country_name) VALUES('MW', '말라위');
INSERT INTO Country(Country_code, Country_name) VALUES('MX', '멕시코');
INSERT INTO Country(Country_code, Country_name) VALUES('MY', '말레이시아');
INSERT INTO Country(Country_code, Country_name) VALUES('MZ', '모잠비크');
INSERT INTO Country(Country_code, Country_name) VALUES('NA', '나미비아');
INSERT INTO Country(Country_code, Country_name) VALUES('NC', '누벨칼레도니');
INSERT INTO Country(Country_code, Country_name) VALUES('NE', '니제르');
INSERT INTO Country(Country_code, Country_name) VALUES('NF', '노퍽섬');
INSERT INTO Country(Country_code, Country_name) VALUES('NG', '나이지리아');
INSERT INTO Country(Country_code, Country_name) VALUES('NI', '니카라과');
INSERT INTO Country(Country_code, Country_name) VALUES('NL', '네덜란드');
INSERT INTO Country(Country_code, Country_name) VALUES('NO', '노르웨이');
INSERT INTO Country(Country_code, Country_name) VALUES('NP', '네팔');
INSERT INTO Country(Country_code, Country_name) VALUES('NR', '나우루');
INSERT INTO Country(Country_code, Country_name) VALUES('NU', '니우에');
INSERT INTO Country(Country_code, Country_name) VALUES('NZ', '뉴질랜드');
INSERT INTO Country(Country_code, Country_name) VALUES('OM', '오만');
INSERT INTO Country(Country_code, Country_name) VALUES('PA', '파나마');
INSERT INTO Country(Country_code, Country_name) VALUES('PE', '페루');
INSERT INTO Country(Country_code, Country_name) VALUES('PF', '프랑스령 폴리네시아');
INSERT INTO Country(Country_code, Country_name) VALUES('PG', '기니');
INSERT INTO Country(Country_code, Country_name) VALUES('PH', '필리핀');
INSERT INTO Country(Country_code, Country_name) VALUES('PK', '파키스탄');
INSERT INTO Country(Country_code, Country_name) VALUES('PL', '폴란드');
INSERT INTO Country(Country_code, Country_name) VALUES('PM', '생피에르 미클롱');
INSERT INTO Country(Country_code, Country_name) VALUES('PN', '핏케언 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('PR', '푸에르토리코');
INSERT INTO Country(Country_code, Country_name) VALUES('PS', '팔레스타인');
INSERT INTO Country(Country_code, Country_name) VALUES('PT', '포르투갈');
INSERT INTO Country(Country_code, Country_name) VALUES('PW', '팔라우');
INSERT INTO Country(Country_code, Country_name) VALUES('PY', '파라과이');
INSERT INTO Country(Country_code, Country_name) VALUES('QA', '카타르');
INSERT INTO Country(Country_code, Country_name) VALUES('RE', '레위니옹');
INSERT INTO Country(Country_code, Country_name) VALUES('RO', '루마니아');
INSERT INTO Country(Country_code, Country_name) VALUES('RS', '세르비아');
INSERT INTO Country(Country_code, Country_name) VALUES('RU', '러시아');
INSERT INTO Country(Country_code, Country_name) VALUES('RW', '르완다');
INSERT INTO Country(Country_code, Country_name) VALUES('SA', '사우디아라비아');
INSERT INTO Country(Country_code, Country_name) VALUES('SB', '솔로몬 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('SC', '세이셸');
INSERT INTO Country(Country_code, Country_name) VALUES('SD', '수단');
INSERT INTO Country(Country_code, Country_name) VALUES('SE', '스웨덴');
INSERT INTO Country(Country_code, Country_name) VALUES('SG', '싱가포르');
INSERT INTO Country(Country_code, Country_name) VALUES('SH', '세인트헬레나');
INSERT INTO Country(Country_code, Country_name) VALUES('SI', '슬로베니아');
INSERT INTO Country(Country_code, Country_name) VALUES('SJ', '스발바르 얀마옌');
INSERT INTO Country(Country_code, Country_name) VALUES('SK', '슬로바키아');
INSERT INTO Country(Country_code, Country_name) VALUES('SL', '시에라리온');
INSERT INTO Country(Country_code, Country_name) VALUES('SM', '산마리노');
INSERT INTO Country(Country_code, Country_name) VALUES('SN', '세네갈');
INSERT INTO Country(Country_code, Country_name) VALUES('SO', '소말리아');
INSERT INTO Country(Country_code, Country_name) VALUES('SR', '수리남');
INSERT INTO Country(Country_code, Country_name) VALUES('SS', '남수단');
INSERT INTO Country(Country_code, Country_name) VALUES('ST', '상투메 프린시페');
INSERT INTO Country(Country_code, Country_name) VALUES('SV', '엘살바도르');
INSERT INTO Country(Country_code, Country_name) VALUES('SX', '신트마르턴');
INSERT INTO Country(Country_code, Country_name) VALUES('SY', '시리아');
INSERT INTO Country(Country_code, Country_name) VALUES('SZ', '에스와티니');
INSERT INTO Country(Country_code, Country_name) VALUES('TC', '터크스 케이커스 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('TD', '차드');
INSERT INTO Country(Country_code, Country_name) VALUES('TF', '프랑스령 남방 및 남극 지역');
INSERT INTO Country(Country_code, Country_name) VALUES('TG', '토고');
INSERT INTO Country(Country_code, Country_name) VALUES('TH', '태국');
INSERT INTO Country(Country_code, Country_name) VALUES('TJ', '타지키스탄');
INSERT INTO Country(Country_code, Country_name) VALUES('TK', '토켈라우');
INSERT INTO Country(Country_code, Country_name) VALUES('TL', '동티모르');
INSERT INTO Country(Country_code, Country_name) VALUES('TM', '투르크메니스탄');
INSERT INTO Country(Country_code, Country_name) VALUES('TN', '튀니지');
INSERT INTO Country(Country_code, Country_name) VALUES('TO', '통가');
INSERT INTO Country(Country_code, Country_name) VALUES('TR', '튀르키예');
INSERT INTO Country(Country_code, Country_name) VALUES('TT', '트리니다드 토바고');
INSERT INTO Country(Country_code, Country_name) VALUES('TV', '투발루');
INSERT INTO Country(Country_code, Country_name) VALUES('TW', '중화민국');
INSERT INTO Country(Country_code, Country_name) VALUES('TZ', '탄자니아');
INSERT INTO Country(Country_code, Country_name) VALUES('UA', '우크라이나');
INSERT INTO Country(Country_code, Country_name) VALUES('UG', '우간다');
INSERT INTO Country(Country_code, Country_name) VALUES('UM', '미국령 군소 제도');
INSERT INTO Country(Country_code, Country_name) VALUES('US', '미국');
INSERT INTO Country(Country_code, Country_name) VALUES('UY', '우루과이');
INSERT INTO Country(Country_code, Country_name) VALUES('UZ', '우즈베키스탄');
INSERT INTO Country(Country_code, Country_name) VALUES('VA', '바티칸 시국');
INSERT INTO Country(Country_code, Country_name) VALUES('VC', '세인트빈센트 그레나딘');
INSERT INTO Country(Country_code, Country_name) VALUES('VE', '베네수엘라');
INSERT INTO Country(Country_code, Country_name) VALUES('VG', '영국령 버진아일랜드');
INSERT INTO Country(Country_code, Country_name) VALUES('VI', '미국령 버진아일랜드');
INSERT INTO Country(Country_code, Country_name) VALUES('VN', '베트남');
INSERT INTO Country(Country_code, Country_name) VALUES('VU', '바누아투');
INSERT INTO Country(Country_code, Country_name) VALUES('WF', '왈리스 푸투나');
INSERT INTO Country(Country_code, Country_name) VALUES('WS', '사모아');
INSERT INTO Country(Country_code, Country_name) VALUES('YE', '예멘');
INSERT INTO Country(Country_code, Country_name) VALUES('YT', '마요트');
INSERT INTO Country(Country_code, Country_name) VALUES('ZA', '남아프리카 공화국');
INSERT INTO Country(Country_code, Country_name) VALUES('ZM', '잠비아');
INSERT INTO Country(Country_code, Country_name) VALUES('ZW', '짐바브웨');

-- City(Korea) Records
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Seoul', '서울');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Busan', '부산');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Daegu', '대구');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Incheon', '인천');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Gwangju', '광주');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Daejeon', '대전');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Ulsan', '울산');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Gyeonggi-do', '경기도');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Gangwon-do', '강원도');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Chungcheongbuk-do', '충청북도');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Chungcheongnam-do', '충청남도');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Jeollabuk-do', '전라북도');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Jeollanam-do', '전라남도');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Gyeongsangbuk-do', '경상북도');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Gyeongsangnam-do', '경상남도');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('KR', 'Jeju-do', '제주도');


-- City(Japan) Records
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Hokkaido', '홋카이도');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Aomori', '아오모리');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Iwate', '이와테');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Miyagi', '미야기');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Akita', '아키타');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Yamagata', '야마가타');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Fukushima', '후쿠시마');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Ibaraki', '이바라키');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Tochigi', '토치기');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Gunma', '군마');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Saitama', '사이타마');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Chiba', '치바');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Tokyo', '도쿄');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Kanagawa', '가나가와');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Niigata', '니가타');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Toyama', '도야마');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Ishikawa', '이시카와');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Fukui', '후쿠이');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Yamanashi', '야마나시');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Nagano', '나가노');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Gifu', '기후');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Shizuoka', '시즈오카');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Aichi', '아이치');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Mie', '미에');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Shiga', '시가');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Kyoto', '교토');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Osaka', '오사카');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Hyogo', '효고');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Nara', '나라');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Wakayama', '와카야마');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Tottori', '돗토리');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Shimane', '시마네');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Okayama', '오카야마');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Hiroshima', '히로시마');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Yamaguchi', '야마구치');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Tokushima', '도쿠시마');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Kagawa', '가가와');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Ehime', '에히메');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Kochi', '고치');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Fukuoka', '후쿠오카');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Saga', '사가');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Nagasaki', '나가사키');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Kumamoto', '구마모토');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Oita', '오이타');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Miyazaki', '미야자키');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Kagoshima', '가고시마');
INSERT INTO City(Country_code, City_name, City_name_translated) VALUES('JP', 'Okinawa', '오키나와');


-- Type Records
INSERT INTO Type(Type_name) VALUES('car_dealer');
INSERT INTO Type(Type_name) VALUES('electric_vehicle_charging_station');
INSERT INTO Type(Type_name) VALUES('car_rental');
INSERT INTO Type(Type_name) VALUES('gas_station');
INSERT INTO Type(Type_name) VALUES('car_repair');
INSERT INTO Type(Type_name) VALUES('parking');
INSERT INTO Type(Type_name) VALUES('car_wash');
INSERT INTO Type(Type_name) VALUES('rest_shop');
INSERT INTO Type(Type_name) VALUES('farm');
INSERT INTO Type(Type_name) VALUES('art_gallery');
INSERT INTO Type(Type_name) VALUES('museum');
INSERT INTO Type(Type_name) VALUES('performing_arts_theater');
INSERT INTO Type(Type_name) VALUES('library');
INSERT INTO Type(Type_name) VALUES('school');
INSERT INTO Type(Type_name) VALUES('preschool');
INSERT INTO Type(Type_name) VALUES('secondary_school');
INSERT INTO Type(Type_name) VALUES('primary_school');
INSERT INTO Type(Type_name) VALUES('university');
INSERT INTO Type(Type_name) VALUES('amusement_center');
INSERT INTO Type(Type_name) VALUES('amusement_park');
INSERT INTO Type(Type_name) VALUES('aquarium');
INSERT INTO Type(Type_name) VALUES('banquet_hall');
INSERT INTO Type(Type_name) VALUES('bowling_alley');
INSERT INTO Type(Type_name) VALUES('casino');
INSERT INTO Type(Type_name) VALUES('community_center');
INSERT INTO Type(Type_name) VALUES('convention_center');
INSERT INTO Type(Type_name) VALUES('cultural_center');
INSERT INTO Type(Type_name) VALUES('dog_park');
INSERT INTO Type(Type_name) VALUES('event_venue');
INSERT INTO Type(Type_name) VALUES('hiking_area');
INSERT INTO Type(Type_name) VALUES('historical_landmark');
INSERT INTO Type(Type_name) VALUES('marina');
INSERT INTO Type(Type_name) VALUES('movie_rental');
INSERT INTO Type(Type_name) VALUES('movie_theater');
INSERT INTO Type(Type_name) VALUES('national_park');
INSERT INTO Type(Type_name) VALUES('night_club');
INSERT INTO Type(Type_name) VALUES('park');
INSERT INTO Type(Type_name) VALUES('tourist_attraction');
INSERT INTO Type(Type_name) VALUES('visitor_center');
INSERT INTO Type(Type_name) VALUES('wedding_venue');
INSERT INTO Type(Type_name) VALUES('zoo');
INSERT INTO Type(Type_name) VALUES('accounting');
INSERT INTO Type(Type_name) VALUES('atm');
INSERT INTO Type(Type_name) VALUES('bank');
INSERT INTO Type(Type_name) VALUES('american_restaurant');
INSERT INTO Type(Type_name) VALUES('bakery');
INSERT INTO Type(Type_name) VALUES('bar');
INSERT INTO Type(Type_name) VALUES('barbecue_restaurant');
INSERT INTO Type(Type_name) VALUES('brazilian_restaurant');
INSERT INTO Type(Type_name) VALUES('breakfast_restaurant');
INSERT INTO Type(Type_name) VALUES('brunch_restaurant');
INSERT INTO Type(Type_name) VALUES('cafe');
INSERT INTO Type(Type_name) VALUES('chinese_restaurant');
INSERT INTO Type(Type_name) VALUES('coffee_shop');
INSERT INTO Type(Type_name) VALUES('fast_food_restaurant');
INSERT INTO Type(Type_name) VALUES('french_restaurant');
INSERT INTO Type(Type_name) VALUES('greek_restaurant');
INSERT INTO Type(Type_name) VALUES('hamburger_restaurant');
INSERT INTO Type(Type_name) VALUES('ice_cream_shop');
INSERT INTO Type(Type_name) VALUES('indian_restaurant');
INSERT INTO Type(Type_name) VALUES('indonesian_restaurant');
INSERT INTO Type(Type_name) VALUES('italian_restaurant');
INSERT INTO Type(Type_name) VALUES('japanese_restaurant');
INSERT INTO Type(Type_name) VALUES('korean_restaurant');
INSERT INTO Type(Type_name) VALUES('lebanese_restaurant');
INSERT INTO Type(Type_name) VALUES('meal_delivery');
INSERT INTO Type(Type_name) VALUES('meal_takeaway');
INSERT INTO Type(Type_name) VALUES('mediterranean_restaurant');
INSERT INTO Type(Type_name) VALUES('mexican_restaurant');
INSERT INTO Type(Type_name) VALUES('middle_eastern_restaurant');
INSERT INTO Type(Type_name) VALUES('pizza_restaurant');
INSERT INTO Type(Type_name) VALUES('ramen_restaurant');
INSERT INTO Type(Type_name) VALUES('restaurant');
INSERT INTO Type(Type_name) VALUES('sandwich_shop');
INSERT INTO Type(Type_name) VALUES('seafood_restaurant');
INSERT INTO Type(Type_name) VALUES('spanish_restaurant');
INSERT INTO Type(Type_name) VALUES('steak_house');
INSERT INTO Type(Type_name) VALUES('sushi_restaurant');
INSERT INTO Type(Type_name) VALUES('thai_restaurant');
INSERT INTO Type(Type_name) VALUES('turkish_restaurant');
INSERT INTO Type(Type_name) VALUES('vegan_restaurant');
INSERT INTO Type(Type_name) VALUES('vegetarian_restaurant');
INSERT INTO Type(Type_name) VALUES('vietnamese_restaurant');
INSERT INTO Type(Type_name) VALUES('city_hall');
INSERT INTO Type(Type_name) VALUES('courthouse');
INSERT INTO Type(Type_name) VALUES('embassy');
INSERT INTO Type(Type_name) VALUES('fire_station');
INSERT INTO Type(Type_name) VALUES('local_government_office');
INSERT INTO Type(Type_name) VALUES('police');
INSERT INTO Type(Type_name) VALUES('post_office');
INSERT INTO Type(Type_name) VALUES('dental_clinic');
INSERT INTO Type(Type_name) VALUES('dentist');
INSERT INTO Type(Type_name) VALUES('doctor');
INSERT INTO Type(Type_name) VALUES('drugstore');
INSERT INTO Type(Type_name) VALUES('hospital');
INSERT INTO Type(Type_name) VALUES('medical_lab');
INSERT INTO Type(Type_name) VALUES('pharmacy');
INSERT INTO Type(Type_name) VALUES('physiotherapist');
INSERT INTO Type(Type_name) VALUES('spa');
INSERT INTO Type(Type_name) VALUES('bed_and_breakfast');
INSERT INTO Type(Type_name) VALUES('campground');
INSERT INTO Type(Type_name) VALUES('camping_cabin');
INSERT INTO Type(Type_name) VALUES('cottage');
INSERT INTO Type(Type_name) VALUES('extended_stay_hotel');
INSERT INTO Type(Type_name) VALUES('farmstay');
INSERT INTO Type(Type_name) VALUES('guest_house');
INSERT INTO Type(Type_name) VALUES('hostel');
INSERT INTO Type(Type_name) VALUES('hotel');
INSERT INTO Type(Type_name) VALUES('lodging');
INSERT INTO Type(Type_name) VALUES('motel');
INSERT INTO Type(Type_name) VALUES('private_guest_room');
INSERT INTO Type(Type_name) VALUES('resort_hotel');
INSERT INTO Type(Type_name) VALUES('rv_park');
INSERT INTO Type(Type_name) VALUES('church');
INSERT INTO Type(Type_name) VALUES('hindu_temple');
INSERT INTO Type(Type_name) VALUES('mosque');
INSERT INTO Type(Type_name) VALUES('synagogue');
INSERT INTO Type(Type_name) VALUES('barber_shop');
INSERT INTO Type(Type_name) VALUES('beauty_salon');
INSERT INTO Type(Type_name) VALUES('cemetery');
INSERT INTO Type(Type_name) VALUES('child_care_agency');
INSERT INTO Type(Type_name) VALUES('consultant');
INSERT INTO Type(Type_name) VALUES('courier_service');
INSERT INTO Type(Type_name) VALUES('electrician');
INSERT INTO Type(Type_name) VALUES('florist');
INSERT INTO Type(Type_name) VALUES('funeral_home');
INSERT INTO Type(Type_name) VALUES('hair_care');
INSERT INTO Type(Type_name) VALUES('hair_salon');
INSERT INTO Type(Type_name) VALUES('insurance_agency');
INSERT INTO Type(Type_name) VALUES('laundry');
INSERT INTO Type(Type_name) VALUES('lawyer');
INSERT INTO Type(Type_name) VALUES('locksmith');
INSERT INTO Type(Type_name) VALUES('moving_company');
INSERT INTO Type(Type_name) VALUES('painter');
INSERT INTO Type(Type_name) VALUES('plumber');
INSERT INTO Type(Type_name) VALUES('real_estate_agency');
INSERT INTO Type(Type_name) VALUES('roofing_contractor');
INSERT INTO Type(Type_name) VALUES('storage');
INSERT INTO Type(Type_name) VALUES('tailor');
INSERT INTO Type(Type_name) VALUES('telecommunications_service_provider');
INSERT INTO Type(Type_name) VALUES('travel_agency');
INSERT INTO Type(Type_name) VALUES('veterinary_care');
INSERT INTO Type(Type_name) VALUES('auto_parts_store');
INSERT INTO Type(Type_name) VALUES('bicycle_store');
INSERT INTO Type(Type_name) VALUES('book_store');
INSERT INTO Type(Type_name) VALUES('cell_phone_store');
INSERT INTO Type(Type_name) VALUES('clothing_store');
INSERT INTO Type(Type_name) VALUES('convenience_store');
INSERT INTO Type(Type_name) VALUES('department_store');
INSERT INTO Type(Type_name) VALUES('discount_store');
INSERT INTO Type(Type_name) VALUES('electronics_store');
INSERT INTO Type(Type_name) VALUES('furniture_store');
INSERT INTO Type(Type_name) VALUES('gift_shop');
INSERT INTO Type(Type_name) VALUES('grocery_store');
INSERT INTO Type(Type_name) VALUES('hardware_store');
INSERT INTO Type(Type_name) VALUES('home_goods_store');
INSERT INTO Type(Type_name) VALUES('home_improvement_store');
INSERT INTO Type(Type_name) VALUES('jewelry_store');
INSERT INTO Type(Type_name) VALUES('liquor_store');
INSERT INTO Type(Type_name) VALUES('market');
INSERT INTO Type(Type_name) VALUES('pet_store');
INSERT INTO Type(Type_name) VALUES('shoe_store');
INSERT INTO Type(Type_name) VALUES('shopping_mall');
INSERT INTO Type(Type_name) VALUES('sporting_goods_store');
INSERT INTO Type(Type_name) VALUES('store');
INSERT INTO Type(Type_name) VALUES('supermarket');
INSERT INTO Type(Type_name) VALUES('wholesaler');
INSERT INTO Type(Type_name) VALUES('athletic_field');
INSERT INTO Type(Type_name) VALUES('fitness_center');
INSERT INTO Type(Type_name) VALUES('golf_course');
INSERT INTO Type(Type_name) VALUES('gym');
INSERT INTO Type(Type_name) VALUES('playground');
INSERT INTO Type(Type_name) VALUES('ski_resort');
INSERT INTO Type(Type_name) VALUES('sports_club');
INSERT INTO Type(Type_name) VALUES('sports_complex');
INSERT INTO Type(Type_name) VALUES('stadium');
INSERT INTO Type(Type_name) VALUES('swimming_pool');
INSERT INTO Type(Type_name) VALUES('airport');
INSERT INTO Type(Type_name) VALUES('bus_station');
INSERT INTO Type(Type_name) VALUES('bus_stop');
INSERT INTO Type(Type_name) VALUES('ferry_terminal');
INSERT INTO Type(Type_name) VALUES('heliport');
INSERT INTO Type(Type_name) VALUES('light_rail_station');
INSERT INTO Type(Type_name) VALUES('park_and_ride');
INSERT INTO Type(Type_name) VALUES('subway_station');
INSERT INTO Type(Type_name) VALUES('taxi_stand');
INSERT INTO Type(Type_name) VALUES('train_station');
INSERT INTO Type(Type_name) VALUES('transit_depot');
INSERT INTO Type(Type_name) VALUES('transit_station');
INSERT INTO Type(Type_name) VALUES('truck_stop');
INSERT INTO Type(Type_name) VALUES('administrative_area_level_3');
INSERT INTO Type(Type_name) VALUES('administrative_area_level_4');
INSERT INTO Type(Type_name) VALUES('administrative_area_level_5');
INSERT INTO Type(Type_name) VALUES('administrative_area_level_6');
INSERT INTO Type(Type_name) VALUES('administrative_area_level_7');
INSERT INTO Type(Type_name) VALUES('archipelago');
INSERT INTO Type(Type_name) VALUES('colloquial_area');
INSERT INTO Type(Type_name) VALUES('continent');
INSERT INTO Type(Type_name) VALUES('establishment');
INSERT INTO Type(Type_name) VALUES('floor');
INSERT INTO Type(Type_name) VALUES('food');
INSERT INTO Type(Type_name) VALUES('general_contractor');
INSERT INTO Type(Type_name) VALUES('geocode');
INSERT INTO Type(Type_name) VALUES('health');
INSERT INTO Type(Type_name) VALUES('intersection');
INSERT INTO Type(Type_name) VALUES('landmark');
INSERT INTO Type(Type_name) VALUES('natural_feature');
INSERT INTO Type(Type_name) VALUES('neighborhood');
INSERT INTO Type(Type_name) VALUES('place_of_worship');
INSERT INTO Type(Type_name) VALUES('plus_code');
INSERT INTO Type(Type_name) VALUES('point_of_interest');
INSERT INTO Type(Type_name) VALUES('political');
INSERT INTO Type(Type_name) VALUES('post_box');
INSERT INTO Type(Type_name) VALUES('postal_code_prefix');
INSERT INTO Type(Type_name) VALUES('postal_code_suffix');
INSERT INTO Type(Type_name) VALUES('postal_town');
INSERT INTO Type(Type_name) VALUES('premise');
INSERT INTO Type(Type_name) VALUES('room');
INSERT INTO Type(Type_name) VALUES('route');
INSERT INTO Type(Type_name) VALUES('street_address');
INSERT INTO Type(Type_name) VALUES('street_number');
INSERT INTO Type(Type_name) VALUES('sublocality');
INSERT INTO Type(Type_name) VALUES('sublocality_level_1');
INSERT INTO Type(Type_name) VALUES('sublocality_level_2');
INSERT INTO Type(Type_name) VALUES('sublocality_level_3');
INSERT INTO Type(Type_name) VALUES('sublocality_level_4');
INSERT INTO Type(Type_name) VALUES('sublocality_level_5');
INSERT INTO Type(Type_name) VALUES('subpremise');
INSERT INTO Type(Type_name) VALUES('town_square');
INSERT INTO Type(Type_name) VALUES('administrative_area_level_1');
INSERT INTO Type(Type_name) VALUES('administrative_area_level_2');
INSERT INTO Type(Type_name) VALUES('country');
INSERT INTO Type(Type_name) VALUES('locality');
INSERT INTO Type(Type_name) VALUES('postal_code');
INSERT INTO Type(Type_name) VALUES('school_district');

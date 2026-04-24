/* 시퀀스 */
CREATE SEQUENCE stock_no_seq START WITH 1;
CREATE SEQUENCE coupon_no_seq START WITH 1;
CREATE SEQUENCE coupon_purchase_no_seq START WITH 1;
CREATE SEQUENCE news_no_seq START WITH 1;
CREATE SEQUENCE get_point_no_seq START WITH 1;
CREATE SEQUENCE order_no_seq START WITH 1;
CREATE SEQUENCE transaction_no_seq START WITH 1;

/* 학생정보 */
CREATE TABLE STUDENTS (
	student_id varchar2(20) NOT NULL, /* 아이디 */
	password varchar2(50) NOT NULL, /* 비밀번호 */
	name varchar2(50) NOT NULL, /* 이름 */
	grade number(1) NOT NULL, /* 학년 */
	class varchar2(20) NOT NULL, /* 반 */
	class_number number(2) NOT NULL, /* 번호(학급번호) */
	register_year number(4) NOT NULL, /* 가입연도 */
	total_coupon number(1) NOT NULL, /* 보유쿠폰(총보유쿠폰수량) */
	total_point number(10) NOT NULL /* 보유포인트(가용포인트) */
);

ALTER TABLE STUDENTS
	ADD
		CONSTRAINT PK_STUDENTS
		PRIMARY KEY (
			student_id
		);

/* 주식거래내역 */
CREATE TABLE TRANSACTION (
	transaction_no number(10) NOT NULL, /* 주식거래 일련번호 */
	transaction_date date NOT NULL, /* 날짜(체결시간) */
	buy_order_no number(10) NOT NULL, /* 주문요청 일련번호(매수한주문) */
	sell_order_no number(10)NULL /* 주문요청 일련번호(매도한주문) */
);

ALTER TABLE TRANSACTION
	ADD
		CONSTRAINT PK_TRANSACTION
		PRIMARY KEY (
			transaction_no
		);

/* 주식기본정보 */
CREATE TABLE STOCKS (
	stock_no number(2) NOT NULL, /* 주식기본정보 일련번호 */
	name varchar2(100) NOT NULL, /* 이름 */
	content varchar2(500) NOT NULL, /* 설명 */
	publication_balance number(3) NOT NULL, /* 발행잔량 */
	publication_price number(10) NOT NULL, /* 발행가격 */
	prev_price number(10) NOT NULL /* 이전가격 */
);

ALTER TABLE STOCKS
	ADD
		CONSTRAINT PK_STOCKS
		PRIMARY KEY (
			stock_no
		);

/* 쿠폰구매내역 */
CREATE TABLE COUPON_PURCHASE (
	coupon_purchase_no number(10) NOT NULL, /* 쿠폰구매 일련번호 */
	purchase_date date NOT NULL, /* 날짜(쿠폰 구매 시간) */
	price number(10) NOT NULL, /* 거래가격(쿠폰 구매당시 가격) */
	name varchar2(50) NOT NULL, /* 쿠폰이름(쿠폰 구매당시 이름) */
	state number(1) NOT NULL, /* 쿠폰상태(사용,미사용) */
	student_id varchar2(20) NOT NULL, /* 아이디 */
	coupon_no number(10) NOT NULL /* 쿠폰기본정보 일련번호 */
);

ALTER TABLE COUPON_PURCHASE
	ADD
		CONSTRAINT PK_COUPON_PURCHASE
		PRIMARY KEY (
			coupon_purchase_no
		);

/* 쿠폰기본정보 */
CREATE TABLE COUPONS (
	coupon_no number(10) NOT NULL, /* 쿠폰기본정보 일련번호 */
	name varchar2(50) NOT NULL, /* 쿠폰이름 */
	price number(10) NOT NULL /* 쿠폰가격 */
);

ALTER TABLE COUPONS
	ADD
		CONSTRAINT PK_COUPONS
		PRIMARY KEY (
			coupon_no
		);

/* 지급내역 */
CREATE TABLE GET_POINT (
	get_point_no number(10) NOT NULL, /* 지급 일련번호 */
	content varchar2(50) NOT NULL, /* 내용(지급내용) */
	point number(20) NOT NULL, /* 지급된 포인트 */
	get_date DATE NOT NULL, /* 날짜(지급 시간) */
	student_id varchar2(20) NOT NULL /* 아이디 */
);

ALTER TABLE GET_POINT
	ADD
		CONSTRAINT PK_GET_POINT
		PRIMARY KEY (
			get_point_no
		);

/* 주문요청 */
CREATE TABLE ORDERS (
	order_no number(10) NOT NULL, /* 주문요청 일련번호 */
	content varchar2(20) NOT NULL, /* 주문내용(매수,매도) */
	price number(10) NOT NULL, /* 주문요청가격(한 주당 가격) */
	amount number(3) NOT NULL, /* 주문요청수량(주문요청 주식수) */
	state varchar2(20) NOT NULL, /* 주문상태(대기,체결,취소) */
	order_date date NOT NULL, /* 날짜(주문요청 날짜) */
	student_id varchar2(20) NOT NULL, /* 아이디 */
	stock_no number(2) NOT NULL /* 주식기본정보 일련번호 */
);

ALTER TABLE ORDERS
	ADD
		CONSTRAINT PK_ORDERS
		PRIMARY KEY (
			order_no
		);

/* 뉴스 */
CREATE TABLE NEWS (
	news_no number(10) NOT NULL, /* 뉴스 일련번호 */
	content varchar2(1000) NOT NULL /* 뉴스내용 */
);

ALTER TABLE NEWS
	ADD
		CONSTRAINT PK_NEWS
		PRIMARY KEY (
			news_no
		);

ALTER TABLE TRANSACTION
	ADD
		CONSTRAINT FK_ORDERS_TO_TRANSACTION
		FOREIGN KEY (
			buy_order_no
		)
		REFERENCES ORDERS (
			order_no
		);

ALTER TABLE TRANSACTION
	ADD
		CONSTRAINT FK_ORDERS_TO_TRANSACTION2
		FOREIGN KEY (
			sell_order_no
		)
		REFERENCES ORDERS (
			order_no
		);

ALTER TABLE COUPON_PURCHASE
	ADD
		CONSTRAINT FK_STUDENTS_TO_COUPON_PURCHASE
		FOREIGN KEY (
			student_id
		)
		REFERENCES STUDENTS (
			student_id
		);

ALTER TABLE COUPON_PURCHASE
	ADD
		CONSTRAINT FK_COUPONS_TO_COUPON_PURCHASE
		FOREIGN KEY (
			coupon_no
		)
		REFERENCES COUPONS (
			coupon_no
		);

ALTER TABLE GET_POINT
	ADD
		CONSTRAINT FK_STUDENTS_TO_GET_POINT
		FOREIGN KEY (
			student_id
		)
		REFERENCES STUDENTS (
			student_id
		);

ALTER TABLE ORDERS
	ADD
		CONSTRAINT FK_STUDENTS_TO_ORDERS
		FOREIGN KEY (
			student_id
		)
		REFERENCES STUDENTS (
			student_id
		);

ALTER TABLE ORDERS
	ADD
		CONSTRAINT FK_STOCKS_TO_ORDERS
		FOREIGN KEY (
			stock_no
		)
		REFERENCES STOCKS (
			stock_no
		);
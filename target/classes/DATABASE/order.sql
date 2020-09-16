ALTER TABLE orderDesc
	DROP
		CONSTRAINT FK_member_TO_orderDesc
		CASCADE;

ALTER TABLE orderDesc
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

DROP INDEX PK_orderDesc;

/* 주문개요 */
DROP TABLE orderDesc 
	CASCADE CONSTRAINTS;

/* 주문개요 */
CREATE TABLE orderDesc (
	onum VARCHAR2(30) NOT NULL, /* 주문번호 */
	idx_fk NUMBER(8) NOT NULL, /* 회원번호 */
	ototalPrice NUMBER(10), /* 총주문금액 */
	ototalPoint NUMBER(10), /* 총주문포인트 */
	opayState VARCHAR2(20), /* 지불상태 */
	odeliver VARCHAR2(20), /* 배송상태 */
	odeliverPrice NUMBER(8), /* 배송비 */
	orderDate DATE, /* 주문날짜 */
	orderMemo VARCHAR2(100), /* 배송시요청사항 */
	opayWay VARCHAR2(30), /* 지불방법 */
	opointUse NUMBER(10) /* 사용적립금 */
);

COMMENT ON TABLE orderDesc IS '주문개요';

COMMENT ON COLUMN orderDesc.onum IS '주문번호';

COMMENT ON COLUMN orderDesc.idx_fk IS '회원번호';

COMMENT ON COLUMN orderDesc.ototalPrice IS '총주문금액';

COMMENT ON COLUMN orderDesc.ototalPoint IS '총주문포인트';

COMMENT ON COLUMN orderDesc.opayState IS '지불상태';

COMMENT ON COLUMN orderDesc.odeliver IS '배송상태';

COMMENT ON COLUMN orderDesc.odeliverPrice IS '배송비';

COMMENT ON COLUMN orderDesc.orderDate IS '주문날짜';

COMMENT ON COLUMN orderDesc.orderMemo IS '배송시요청사항';

COMMENT ON COLUMN orderDesc.opayWay IS '지불방법';

COMMENT ON COLUMN orderDesc.opointUse IS '사용적립금';

CREATE UNIQUE INDEX PK_orderDesc
	ON orderDesc (
		onum ASC
	);

ALTER TABLE orderDesc
	ADD
		CONSTRAINT PK_orderDesc
		PRIMARY KEY (
			onum
		);

ALTER TABLE orderDesc
	ADD
		CONSTRAINT FK_member_TO_orderDesc
		FOREIGN KEY (
			idx_fk
		)
		REFERENCES member (
			idx
		);
    
----------------------------

ALTER TABLE order_Product
	DROP
		CONSTRAINT FK_Product_TO_order_Product
		CASCADE;

ALTER TABLE order_Product
	DROP
		CONSTRAINT FK_orderDesc_TO_order_Product
		CASCADE;

ALTER TABLE order_Product
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

DROP INDEX PK_order_Product;

/* 주문상품 */
DROP TABLE order_Product 
	CASCADE CONSTRAINTS;

/* 주문상품 */
CREATE TABLE order_Product (
	pnum_fk NUMBER(8) NOT NULL, /* 상품번호 */
	onum VARCHAR2(30) NOT NULL, /* 주문번호 */
	saleprice NUMBER(10), /* 상품단가 */
	opoint NUMBER(10), /* 적립포인트 */
	oqty NUMBER(10), /* 주문수량 */
	pimage VARCHAR2(200) /* 상품이미지 */
);

COMMENT ON TABLE order_Product IS '주문상품';

COMMENT ON COLUMN order_Product.pnum_fk IS '상품번호';

COMMENT ON COLUMN order_Product.onum IS '주문번호';

COMMENT ON COLUMN order_Product.saleprice IS '상품단가';

COMMENT ON COLUMN order_Product.opoint IS '적립포인트';

COMMENT ON COLUMN order_Product.oqty IS '주문수량';

COMMENT ON COLUMN order_Product.pimage IS '상품이미지';

CREATE UNIQUE INDEX PK_order_Product
	ON order_Product (
		pnum_fk ASC,
		onum ASC
	);

ALTER TABLE order_Product
	ADD
		CONSTRAINT PK_order_Product
		PRIMARY KEY (
			pnum_fk,
			onum
		);

ALTER TABLE order_Product
	ADD
		CONSTRAINT FK_Product_TO_order_Product
		FOREIGN KEY (
			pnum_fk
		)
		REFERENCES Product (
			pnum
		);

ALTER TABLE order_Product
	ADD
		CONSTRAINT FK_orderDesc_TO_order_Product
		FOREIGN KEY (
			onum
		)
		REFERENCES orderDesc (
			onum
		);
    
    
----------------------------

ALTER TABLE Receiver
	DROP
		CONSTRAINT FK_orderDesc_TO_Receiver
		CASCADE;

ALTER TABLE Receiver
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

DROP INDEX PK_Receiver;

/* 수령자 */
DROP TABLE Receiver 
	CASCADE CONSTRAINTS;

/* 수령자 */
CREATE TABLE Receiver (
	onum VARCHAR2(30) NOT NULL, /* 주문번호 */
	name VARCHAR2(30) NOT NULL, /* 이름 */
	hp1 CHAR(3) NOT NULL, /* 연락처1 */
	hp2 CHAR(4) NOT NULL, /* 연락처2 */
	hp3 CHAR(4) NOT NULL, /* 연락처3 */
	zipcode CHAR(5) NOT NULL, /* 우편번호 */
	addr1 VARCHAR2(200) NOT NULL, /* 주소1 */
	addr2 VARCHAR2(200) /* 주소2 */
);

COMMENT ON TABLE Receiver IS '수령자';

COMMENT ON COLUMN Receiver.onum IS '주문번호';

COMMENT ON COLUMN Receiver.name IS '이름';

COMMENT ON COLUMN Receiver.hp1 IS '연락처1';

COMMENT ON COLUMN Receiver.hp2 IS '연락처2';

COMMENT ON COLUMN Receiver.hp3 IS '연락처3';

COMMENT ON COLUMN Receiver.zipcode IS '우편번호';

COMMENT ON COLUMN Receiver.addr1 IS '주소1';

COMMENT ON COLUMN Receiver.addr2 IS '주소2';

CREATE UNIQUE INDEX PK_Receiver
	ON Receiver (
		onum ASC
	);

ALTER TABLE Receiver
	ADD
		CONSTRAINT PK_Receiver
		PRIMARY KEY (
			onum
		);

ALTER TABLE Receiver
	ADD
		CONSTRAINT FK_orderDesc_TO_Receiver
		FOREIGN KEY (
			onum
		)
		REFERENCES orderDesc (
			onum
		);
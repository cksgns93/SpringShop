ALTER TABLE Cart
	DROP
		CONSTRAINT FK_member_TO_Cart
		CASCADE;

ALTER TABLE Cart
	DROP
		CONSTRAINT FK_Product_TO_Cart
		CASCADE;

ALTER TABLE Cart
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

DROP INDEX PK_Cart;

/* 장바구니 */
DROP TABLE Cart 
	CASCADE CONSTRAINTS;

/* 장바구니 */
CREATE TABLE Cart (
	cartNum NUMBER(8) NOT NULL, /* 장바구니번호 */
	oqty NUMBER(8) NOT NULL, /* 수량 */
	indate DATE, /* 날짜 */
	idx NUMBER(8) NOT NULL, /* 회원번호 */
	pnum NUMBER(8) NOT NULL /* 상품번호 */
);

COMMENT ON TABLE Cart IS '장바구니';

COMMENT ON COLUMN Cart.cartNum IS '장바구니번호';

COMMENT ON COLUMN Cart.oqty IS '수량';

COMMENT ON COLUMN Cart.indate IS '날짜';

COMMENT ON COLUMN Cart.idx IS '회원번호';

COMMENT ON COLUMN Cart.pnum IS '상품번호';

CREATE UNIQUE INDEX PK_Cart
	ON Cart (
		cartNum ASC
	);

ALTER TABLE Cart
	ADD
		CONSTRAINT PK_Cart
		PRIMARY KEY (
			cartNum
		);

ALTER TABLE Cart
	ADD
		CONSTRAINT FK_member_TO_Cart
		FOREIGN KEY (
			idx
		)
		REFERENCES member (
			idx
		);

ALTER TABLE Cart
	ADD
		CONSTRAINT FK_Product_TO_Cart
		FOREIGN KEY (
			pnum
		)
		REFERENCES Product (
			pnum
		);
		
DROP SEQUENCE Cart_seq;

CREATE SEQUENCE Cart_seq NOCACHE;
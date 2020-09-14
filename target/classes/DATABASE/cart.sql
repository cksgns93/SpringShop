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

/* ��ٱ��� */
DROP TABLE Cart 
	CASCADE CONSTRAINTS;

/* ��ٱ��� */
CREATE TABLE Cart (
	cartNum NUMBER(8) NOT NULL, /* ��ٱ��Ϲ�ȣ */
	oqty NUMBER(8) NOT NULL, /* ���� */
	indate DATE, /* ��¥ */
	idx NUMBER(8) NOT NULL, /* ȸ����ȣ */
	pnum NUMBER(8) NOT NULL /* ��ǰ��ȣ */
);

COMMENT ON TABLE Cart IS '��ٱ���';

COMMENT ON COLUMN Cart.cartNum IS '��ٱ��Ϲ�ȣ';

COMMENT ON COLUMN Cart.oqty IS '����';

COMMENT ON COLUMN Cart.indate IS '��¥';

COMMENT ON COLUMN Cart.idx IS 'ȸ����ȣ';

COMMENT ON COLUMN Cart.pnum IS '��ǰ��ȣ';

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
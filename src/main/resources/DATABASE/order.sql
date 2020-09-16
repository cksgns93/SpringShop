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

/* �ֹ����� */
DROP TABLE orderDesc 
	CASCADE CONSTRAINTS;

/* �ֹ����� */
CREATE TABLE orderDesc (
	onum VARCHAR2(30) NOT NULL, /* �ֹ���ȣ */
	idx_fk NUMBER(8) NOT NULL, /* ȸ����ȣ */
	ototalPrice NUMBER(10), /* ���ֹ��ݾ� */
	ototalPoint NUMBER(10), /* ���ֹ�����Ʈ */
	opayState VARCHAR2(20), /* ���һ��� */
	odeliver VARCHAR2(20), /* ��ۻ��� */
	odeliverPrice NUMBER(8), /* ��ۺ� */
	orderDate DATE, /* �ֹ���¥ */
	orderMemo VARCHAR2(100), /* ��۽ÿ�û���� */
	opayWay VARCHAR2(30), /* ���ҹ�� */
	opointUse NUMBER(10) /* ��������� */
);

COMMENT ON TABLE orderDesc IS '�ֹ�����';

COMMENT ON COLUMN orderDesc.onum IS '�ֹ���ȣ';

COMMENT ON COLUMN orderDesc.idx_fk IS 'ȸ����ȣ';

COMMENT ON COLUMN orderDesc.ototalPrice IS '���ֹ��ݾ�';

COMMENT ON COLUMN orderDesc.ototalPoint IS '���ֹ�����Ʈ';

COMMENT ON COLUMN orderDesc.opayState IS '���һ���';

COMMENT ON COLUMN orderDesc.odeliver IS '��ۻ���';

COMMENT ON COLUMN orderDesc.odeliverPrice IS '��ۺ�';

COMMENT ON COLUMN orderDesc.orderDate IS '�ֹ���¥';

COMMENT ON COLUMN orderDesc.orderMemo IS '��۽ÿ�û����';

COMMENT ON COLUMN orderDesc.opayWay IS '���ҹ��';

COMMENT ON COLUMN orderDesc.opointUse IS '���������';

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

/* �ֹ���ǰ */
DROP TABLE order_Product 
	CASCADE CONSTRAINTS;

/* �ֹ���ǰ */
CREATE TABLE order_Product (
	pnum_fk NUMBER(8) NOT NULL, /* ��ǰ��ȣ */
	onum VARCHAR2(30) NOT NULL, /* �ֹ���ȣ */
	saleprice NUMBER(10), /* ��ǰ�ܰ� */
	opoint NUMBER(10), /* ��������Ʈ */
	oqty NUMBER(10), /* �ֹ����� */
	pimage VARCHAR2(200) /* ��ǰ�̹��� */
);

COMMENT ON TABLE order_Product IS '�ֹ���ǰ';

COMMENT ON COLUMN order_Product.pnum_fk IS '��ǰ��ȣ';

COMMENT ON COLUMN order_Product.onum IS '�ֹ���ȣ';

COMMENT ON COLUMN order_Product.saleprice IS '��ǰ�ܰ�';

COMMENT ON COLUMN order_Product.opoint IS '��������Ʈ';

COMMENT ON COLUMN order_Product.oqty IS '�ֹ�����';

COMMENT ON COLUMN order_Product.pimage IS '��ǰ�̹���';

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

/* ������ */
DROP TABLE Receiver 
	CASCADE CONSTRAINTS;

/* ������ */
CREATE TABLE Receiver (
	onum VARCHAR2(30) NOT NULL, /* �ֹ���ȣ */
	name VARCHAR2(30) NOT NULL, /* �̸� */
	hp1 CHAR(3) NOT NULL, /* ����ó1 */
	hp2 CHAR(4) NOT NULL, /* ����ó2 */
	hp3 CHAR(4) NOT NULL, /* ����ó3 */
	zipcode CHAR(5) NOT NULL, /* �����ȣ */
	addr1 VARCHAR2(200) NOT NULL, /* �ּ�1 */
	addr2 VARCHAR2(200) /* �ּ�2 */
);

COMMENT ON TABLE Receiver IS '������';

COMMENT ON COLUMN Receiver.onum IS '�ֹ���ȣ';

COMMENT ON COLUMN Receiver.name IS '�̸�';

COMMENT ON COLUMN Receiver.hp1 IS '����ó1';

COMMENT ON COLUMN Receiver.hp2 IS '����ó2';

COMMENT ON COLUMN Receiver.hp3 IS '����ó3';

COMMENT ON COLUMN Receiver.zipcode IS '�����ȣ';

COMMENT ON COLUMN Receiver.addr1 IS '�ּ�1';

COMMENT ON COLUMN Receiver.addr2 IS '�ּ�2';

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
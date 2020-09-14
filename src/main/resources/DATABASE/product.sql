drop table product;

create table product(
   pnum number(8) constraint product_pk primary key, --상품번호
   upCg_code number(8) references upCategory(upCg_code), --상위 카테고리코드
   downCg_code number(8) references downCategory(downCg_code), --하위 카테고리코드
   pname varchar2(100) not null, --상품명
   pimage1 varchar2(200) default 'noimage.png', --상품이미지1
   pimage2 varchar2(200) default 'noimage.png', --상품이미지1
   pimage3 varchar2(200) default 'noimage.png', --상품이미지1
   price number(8) default 0, --상품 정가
   saleprice number(8) default 0, --판매가
   pqty number(5), --보유 수량
   point number(8) default 0, --적립 포인트
   pspec varchar2(20), --상품 스펙 (HIT,NEW, BEST...)
   pcontents varchar2(1000), --상품 설명
   pcompany varchar2(30), --제조사
   pindate date default sysdate
);

drop sequence product_seq;

create sequence product_seq nocache;

insert into product values(product_seq.nextval,1,1,'아오리사과','apple1.png','apple2.png',
'apple3.png',20000,18000,100,50,'HIT','맛있어요, 신선해요!!','신선농장',sysdate);
insert into product values(product_seq.nextval,1,1,'수박','water1.png','water2.png',
'water3.png',25000,15000,300,150,'HIT','맛있어요, 신선해요!!','신선농장',sysdate);

insert into product values(product_seq.nextval,2,3,'Lg노트북','note1.png','note2.png',
'note3.png',2000000,1700000,200,500,'HIT','성능좋은 노트북','LG',sysdate);

insert into product values(product_seq.nextval,2,3,'삼성노트북','note4.png','note4.png',
'note6.png',2200000,1800000,300,550,'NEW','성능좋은 노트북','LG',sysdate);
commit;






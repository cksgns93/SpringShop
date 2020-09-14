drop table upCategory;

--상위 카테고리 테이블
create table upCategory(
  upCg_code number(8) constraint upCategory_pk primary key, --상위 카테고리 코드
  upCg_name varchar2(30) not null --상위 카테고리명
);

drop sequence upCategory_seq;

create sequence upCategory_seq
start with 1 increment by 1 nocache;

drop table downCategory;

create table downCategory(
  downCg_code number(8) constraint downCategory_pk primary key, --하위 카테고리 코드
  downCg_name varchar2(30) not null, --하위 카테고리명
  upCg_code number(8) constraint upCg_code_fk references upCategory(upCg_code)
);

drop sequence downCategory_seq;

create sequence downCategory_seq nocache;

insert into upCategory values(upCategory_seq.nextval,'식품.생활용품');
insert into upCategory values(upCategory_seq.nextval,'디지털.전자가전');
commit;

select * from upCategory;

insert into downCategory values(downCategory_seq.nextval,'신선식품', 1);
insert into downCategory values(downCategory_seq.nextval,'세제', 1);

insert into downCategory values(downCategory_seq.nextval,'노트북', 2);
insert into downCategory values(downCategory_seq.nextval,'냉장고', 2);

commit;

select * from downCategory;









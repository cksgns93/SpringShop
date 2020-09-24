package com.tis.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class BoardVO implements Serializable {
	
	private String mode;//글쓰기(write), 답변글쓰기(rewrite), 글수정(edit)
	
	private Integer idx;
	private String name;
	private String pwd;
	private String subject;
	private String content;
	
	private java.sql.Timestamp wdate;
	private int readnum;
	private String filename;	  
	private String originFilename;	
	private long filesize;
	
	private int refer;
	private int lev;
	private int sunbun;
	
	private int newImg; //24시간 이내 쓴 글에 new 표시위한 프로퍼티
	

}
	
	//setter, getter-----------
	
	
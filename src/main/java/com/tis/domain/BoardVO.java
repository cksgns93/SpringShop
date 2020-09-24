package com.tis.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class BoardVO implements Serializable {
	
	private String mode;//�۾���(write), �亯�۾���(rewrite), �ۼ���(edit)
	
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
	
	private int newImg; //24�ð� �̳� �� �ۿ� new ǥ������ ������Ƽ
	

}
	
	//setter, getter-----------
	
	
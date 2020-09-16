package com.tis.domain;

import lombok.Data;

@Data
public class ReceiverVO {
	
	private String onum;
	private String name;
	private String hp1,hp2,hp3;
	private String zipcode;
	private String addr1,addr2;
	
	public String getAllHp() {
		return hp1+"-"+hp2+"-"+hp3;
	}
	public String getAllAddr() {
		return "["+zipcode+"] "+addr1+" "+addr2;
	}
}

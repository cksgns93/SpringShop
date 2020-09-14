package com.tis.domain;

import lombok.Data;

@Data
public class UserVO {

	private int idx;
	private String name;
	private String userid;
	private String pwd;
	private String hp1,hp2,hp3;
	private String post;
	private String addr1, addr2;
	private java.sql.Date indate;
	private int mileage;
	private int mstate;

	public String getAllHp() {
		return hp1+"-"+hp2+"-"+hp3;
	}
	public String getAllAddr() {
		return "["+post+"]"+addr1+" "+addr2;
	}
}

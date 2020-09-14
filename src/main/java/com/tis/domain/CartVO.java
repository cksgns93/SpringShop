package com.tis.domain;

import java.io.Serializable;

public class CartVO implements Serializable {
	
	private String cartNum;//��ٱ��Ϲ�ȣ
	private int oqty;//��ٱ��� ����
	private int idx; //ȸ����ȣ
	private int pnum;//��ǰ��ȣ
	private java.sql.Date indate;//�����
	
	private String pname; //��ǰ��
	private String pimage1;
	private int price;
	private int saleprice;
	private int point;
	
	private int totalPrice; //�ǸŰ�*���� =>�ݾ�
	private int totalPoint; 
	
	//��ٱ��� �Ѿװ� ������Ʈ
	private int cartTotalPrice;
	private int cartTotalPoint;
	
	public CartVO() {
		
	}

	public CartVO(String cartNum, int oqty, int idx, int pnum) {
		super();
		this.cartNum = cartNum;
		this.oqty = oqty;
		this.idx = idx;
		this.pnum = pnum;
	}

	public String getCartNum() {
		return cartNum;
	}

	public void setCartNum(String cartNum) {
		this.cartNum = cartNum;
	}

	public int getOqty() {
		return oqty;
	}

	public void setOqty(int oqty) {
		this.oqty = oqty;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getPnum() {
		return pnum;
	}

	public void setPnum(int pnum) {
		this.pnum = pnum;
	}

	public java.sql.Date getIndate() {
		return indate;
	}

	public void setIndate(java.sql.Date indate) {
		this.indate = indate;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPimage1() {
		return pimage1;
	}

	public void setPimage1(String pimage1) {
		this.pimage1 = pimage1;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getCartTotalPrice() {
		return cartTotalPrice;
	}

	public void setCartTotalPrice(int cartTotalPrice) {
		this.cartTotalPrice = cartTotalPrice;
	}

	public int getCartTotalPoint() {
		return cartTotalPoint;
	}

	public void setCartTotalPoint(int cartTotalPoint) {
		this.cartTotalPoint = cartTotalPoint;
	}

	@Override
	public String toString() {
		return "CartVO [cartNum=" + cartNum + ", oqty=" + oqty + ", idx=" + idx + ", pnum=" + pnum + "]";
	}
	
	
}

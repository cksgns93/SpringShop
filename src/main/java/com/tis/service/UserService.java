package com.tis.service;

import java.util.List;

import com.tis.domain.NotUserException;
import com.tis.domain.UserVO;

public interface UserService {
	
	int createUser(UserVO user);
	boolean idCheck(String userid);
	
	List<UserVO> listUser();
	UserVO getUser(int idx);
	UserVO findUser(UserVO user); //�˻������� ���� ȸ������ ��������
	
	UserVO loginCheck(UserVO user) throws NotUserException;
	
	int updateMileage(int idx, int opoint);
	int getUserCount();
	int deleteUser(int idx);
	
}

package com.tis.mapper;

import java.util.List;
import java.util.Map;

import com.tis.domain.NotUserException;
import com.tis.domain.UserVO;
//UserMapper.xml�� ���ӽ����̽��� com.tis.mapper.UserMapper�� �־�� �Ѵ�.
public interface UserMapper {

	int createUser(UserVO user);
	Integer idCheck(String userid);
	
	List<UserVO> listUser();
	UserVO getUser(int idx);
	UserVO findUser(UserVO user); //�˻������� ���� ȸ������ ��������
	
	UserVO loginCheck(UserVO user) throws NotUserException;
	
	//int updateMileage(int idx, int opoint);
	int updateMileage(Map<String, Integer> map);
	int getUserCount();
	int deleteUser(int idx);
}

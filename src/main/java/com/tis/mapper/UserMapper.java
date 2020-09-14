package com.tis.mapper;

import java.util.List;
import java.util.Map;

import com.tis.domain.NotUserException;
import com.tis.domain.UserVO;
//UserMapper.xml에 네임스페이스는 com.tis.mapper.UserMapper로 주어야 한다.
public interface UserMapper {

	int createUser(UserVO user);
	Integer idCheck(String userid);
	
	List<UserVO> listUser();
	UserVO getUser(int idx);
	UserVO findUser(UserVO user); //검색유형에 따른 회원정보 가져오기
	
	UserVO loginCheck(UserVO user) throws NotUserException;
	
	//int updateMileage(int idx, int opoint);
	int updateMileage(Map<String, Integer> map);
	int getUserCount();
	int deleteUser(int idx);
}

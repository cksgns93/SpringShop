package com.tis.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tis.domain.NotUserException;
import com.tis.domain.UserVO;
import com.tis.mapper.UserMapper;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Inject
	private UserMapper userMapper;
	
	@Override
	public int getUserCount() {
		return userMapper.getUserCount();
	}
	
	@Override
	public int createUser(UserVO user) {	
		return userMapper.createUser(user);
	}

	@Override
	public boolean idCheck(String userid) {
		Integer idx= this.userMapper.idCheck(userid);
		//ȸ����ȣ�� ��ȯ�ϸ� "���Ұ�"=>false
		//ȸ����ȣ�� null�� ��ȯ�ϸ� "��밡��" => true;
		boolean bool=(idx==null)? true:false;
		return bool;
	}

	@Override
	public List<UserVO> listUser() {	
		return this.userMapper.listUser();
	}

	@Override
	public UserVO getUser(int idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVO findUser(UserVO user) {		
		return this.userMapper.findUser(user);
	}

	@Override
	public UserVO loginCheck(UserVO user) throws NotUserException {
		//ȸ�� ���̵�� ȸ�������� ��������
		UserVO tmpUser=this.findUser(user);
		if(tmpUser==null) {
			//���̵� �������� �ʴ� ���
			throw new NotUserException("�������� �ʴ� ���̵��Դϴ�.");
		}
		if(!tmpUser.getPwd().equals(user.getPwd())) {
			throw new NotUserException("��й�ȣ�� ��ġ���� �ʾƿ�");
		}
		return tmpUser;
	}

	@Override
	public int updateMileage(int idx, int opoint) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int deleteUser(int idx) {
		return this.userMapper.deleteUser(idx);
	}


}

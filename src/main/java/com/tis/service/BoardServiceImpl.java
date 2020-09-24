package com.tis.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tis.domain.BoardVO;
import com.tis.domain.PagingVO;
import com.tis.mapper.BoardMapper;

@Service("")
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardMapper boardMapper;

	@Override
	public int insertBoard(BoardVO board) {
		return this.boardMapper.insertBoard(board);
	}

	@Override
	public List<BoardVO> selectBoardAll(Map<String, Integer> map) {
		return this.boardMapper.selectBoardAll(map);
	}

	@Override
	public List<BoardVO> selectBoardAllPaging(PagingVO paging) {
		return this.boardMapper.selectBoardAllPaging(paging);
	}

	@Override
	public List<BoardVO> findBoard(PagingVO paging) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalCount() {
		return 0;
	}

	@Override
	public int getTotalCount(PagingVO paging) {
		return this.boardMapper.getTotalCount(paging);
	}

	@Override
	public BoardVO selectBoardByIdx(Integer idx) {
		return this.boardMapper.selectBoardByIdx(idx);
	}

	@Override
	public int updateReadnum(Integer idx) {
		return this.boardMapper.updateReadnum(idx);
	}

	@Override
	public String selectPwd(Integer idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteBoard(Integer idx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int rewriteBoard(BoardVO board) {
		//1. �θ�(����)���� refer, lev, sunbun �� �������� (select)
		BoardVO parent = boardMapper.selectRefLevSunbun(board.getIdx());
		//2. ������ �޸� �亯�۵��� �ִٸ� sunbun�� �ϳ��� �ڷ� �о�� ����
		//   ��? ���� �� �亯���� ������� �ϹǷ�(update��)
		boardMapper.updateSunbun(parent);
		//3. ���� �� �亯���� insert �Ѵ� (insert��)
		//   �� �� �θ�� �׷��ȣ (refer)�� �����ϰ�, lev�� �θ��� lev+1, sunbun�� �θ��� sunbun+1
		board.setRefer(parent.getRefer());//�θ�� ���� �۱׷� ��ȣ ����
		board.setLev(parent.getLev()+1);
		board.setSunbun(parent.getSunbun()+1);
		return this.boardMapper.rewriteBoard(board);
	}

	@Override
	public BoardVO selectRefLevSunbun(int idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateSunbun(BoardVO parent) {
		// TODO Auto-generated method stub
		return 0;
	}

}

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
		//1. 부모(원글)글의 refer, lev, sunbun 값 가져오기 (select)
		BoardVO parent = boardMapper.selectRefLevSunbun(board.getIdx());
		//2. 기존에 달린 답변글들이 있다면 sunbun을 하나씩 뒤로 밀어내기 하자
		//   왜? 내가 쓴 답변글이 끼어들어야 하므로(update문)
		boardMapper.updateSunbun(parent);
		//3. 내가 쓴 답변글을 insert 한다 (insert문)
		//   이 때 부모글 그룹번호 (refer)와 동일하게, lev은 부모의 lev+1, sunbun도 부모의 sunbun+1
		board.setRefer(parent.getRefer());//부모와 같은 글그룹 번호 지정
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

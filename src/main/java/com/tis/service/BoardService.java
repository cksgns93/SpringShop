package com.tis.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tis.domain.BoardVO;
import com.tis.domain.PagingVO;

@Service
public interface BoardService {
	int insertBoard(BoardVO board);
	List<BoardVO> selectBoardAll(Map<String,Integer> map);
	
	List<BoardVO> findBoard(PagingVO paging);
	
	int getTotalCount();
	int getTotalCount(PagingVO paging);

	BoardVO selectBoardByIdx(Integer idx);	
	int updateReadnum(Integer idx);
	
	String selectPwd(Integer idx);
	int deleteBoard(Integer idx);
	int updateBoard(BoardVO board);

	int rewriteBoard(BoardVO board); 
	BoardVO selectRefLevSunbun(int idx);
	int updateSunbun(BoardVO parent);
	List<BoardVO> selectBoardAllPaging(PagingVO paging);
}/////////////////////////////////////






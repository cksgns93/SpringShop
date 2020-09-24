package com.tis.mapper;

import java.util.List;
import java.util.Map;

import com.tis.domain.BoardVO;
import com.tis.domain.PagingVO;

public interface BoardMapper {

	int insertBoard(BoardVO board);

	List<BoardVO> selectBoardAll(Map<String, Integer> map);

	int getTotalCount(PagingVO paging);

	List<BoardVO> selectBoardAllPaging(PagingVO paging);

	int updateReadnum(Integer idx);

	BoardVO selectBoardByIdx(Integer idx);

	int rewriteBoard(BoardVO board);

	BoardVO selectRefLevSunbun(Integer idx);

	void updateSunbun(BoardVO parent);


}

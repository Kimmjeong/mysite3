package com.hanains.mysite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanains.mysite.vo.BoardListVo;
import com.hanains.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
		
	// 전체 글목록
	public List<BoardListVo> getList(){
		List<BoardListVo> list=sqlSession.selectList("board.getbyBoardList");
		return list;
	}
		
	// 글쓰기
	public void insert(BoardVo vo){
		sqlSession.selectOne("board.insert", vo);
	}

	// 글보기
	public BoardVo view(Long no){
		BoardVo vo=sqlSession.selectOne("board.view",no);
		return vo;
	}

	// 글삭제
	public void delete(Long no, Long memberNo){
		
		Map<String, Object> map=new HashMap<>();
		map.put("no", no);
		map.put("memberNo", memberNo);
		
		sqlSession.selectOne("board.delete",map);
	}

	// 글수정
	public void update(BoardVo vo){
		sqlSession.selectOne("board.update",vo);
	}

	// 조회수 증가
	public void viewCount(Long no){
		sqlSession.selectOne("board.viewCount",no);
	}

}

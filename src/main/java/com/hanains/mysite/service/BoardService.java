package com.hanains.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanains.mysite.dao.BoardDao;
import com.hanains.mysite.vo.BoardListVo;
import com.hanains.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	public List<BoardListVo> list(){
		List<BoardListVo> list=boardDao.getList();
		return list;
	}
	
	public void insert(BoardVo vo){
		boardDao.insert(vo);
	}
	
	public BoardVo view(Long no){
		BoardVo vo=boardDao.view(no);
		return vo;
	}
	
	public void delete(Long no, Long member_no){
		boardDao.delete(no, member_no);
	}
	
	public void update(BoardVo vo){
		boardDao.update(vo);
	}
	
}

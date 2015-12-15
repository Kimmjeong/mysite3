package com.hanains.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanains.mysite.service.BoardService;
import com.hanains.mysite.vo.BoardListVo;
import com.hanains.mysite.vo.BoardVo;
import com.hanains.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/") // 리스트
	public String list(Model model){
		List<BoardListVo> list=boardService.list();
		model.addAttribute("list",list);
		return "/board/list";
	}
	
	@RequestMapping("/writeform") // 글쓰기 폼
	public String writeform(){
		return "/board/write";
	}
	
	@RequestMapping("/write") // 글쓰기
	public String write(HttpSession session, @ModelAttribute BoardVo vo){
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		if(authUser!=null){
			vo.setMemberNo(authUser.getNo());
			boardService.insert(vo);
		}
		return "redirect:/board/";
	}
	
	@RequestMapping("/view/{no}") // 글보기
	public String view(@PathVariable("no") Long no, Model model){
		BoardVo writing=boardService.view(no);
		model.addAttribute("writing",writing);
		return "/board/view";
	}
	
	@RequestMapping("/modifyform/{no}") //글수정 폼
	public String modifyform(@PathVariable("no") Long no, Model model,HttpSession session){
		
		BoardVo writing=boardService.view(no);
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		
		if(authUser==null || authUser.getNo()!=writing.getMemberNo()){
			return "redirect:/board/view/"+no;
		}
		
		model.addAttribute("writing",writing);
		return "/board/modify";
		
	}
	
	@RequestMapping("/modify") // 글수정
	public String modify(@ModelAttribute BoardVo vo, @RequestParam(value = "no", required = true, defaultValue = "") Long no){
		boardService.update(vo);
		return "redirect:/board/view/"+no;
	}
	
	@RequestMapping("/delete/{no}&{memberNo}") // 삭제
	public String delete(HttpSession session, @PathVariable("no") Long no, @PathVariable("memberNo") Long memberNo){
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		if (authUser != null) {
			if (authUser.getNo().equals(memberNo))
				boardService.delete(no, memberNo);

		}
		return "redirect:/board/";
	}
	
}

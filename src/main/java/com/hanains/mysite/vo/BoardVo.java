package com.hanains.mysite.vo;

public class BoardVo {
	
	private Long no;
	private String title;
	private String content;
	private Long memberNo;
	private Long viewCnt;
	private String regDate;

	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(Long memberNo) {
		this.memberNo = memberNo;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public Long getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(Long viewCnt) {
		this.viewCnt = viewCnt;
	}


}

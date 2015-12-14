package com.hanains.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hanains.mysite.vo.BoardListVo;
import com.hanains.mysite.vo.BoardVo;

public class BoardDao {
	
	private Connection getConnection() throws SQLException {
		Connection connection = null;
		
		try {
			//1.드라이버 로딩
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
		
			//2.커넥션 만들기(ORACLE DB)
			String dbURL  = "jdbc:oracle:thin:@localhost:1522:xe";
			connection = DriverManager.getConnection( dbURL, "webdb", "webdb" );
			
		} catch( ClassNotFoundException ex ){
			System.out.println( "드라이버 로딩 실패-" + ex );
		}
		
		return connection;
	}
	
	// 전체 글목록
	public List<BoardListVo> getList(){
			
			List<BoardListVo> list=new ArrayList<>();
			
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			
			try {
				conn=getConnection();
				stmt=conn.createStatement();
				
				String sql="select a.no, a.title, a.member_no, b.name as member_name, a.view_cnt, to_char(a.reg_date, 'yyyy-mm-dd hh:mi:ss') "
						+ "from board a, member b where a.member_no = b.no "
						+ "order by a.reg_date desc";
				
				rs=stmt.executeQuery(sql);
				
				while(rs.next()){
					BoardListVo vo=new BoardListVo();
					
					Long no=rs.getLong(1);
					String title=rs.getString(2);
					Long member_no=rs.getLong(3);
					String member_name=rs.getString(4);
					Long view_cnt=rs.getLong(5);
					String reg_date=rs.getString(6);
					
					vo.setNo(no);
					vo.setTitle(title);
					vo.setMember_no(member_no);
					vo.setMember_name(member_name);
					vo.setView_cnt(view_cnt);
					vo.setReg_date(reg_date);
					
					list.add(vo);
					
				}
			} catch (SQLException e) {
				System.out.println("에러 - "+e);
			} finally {
				try {
					
					if (rs != null) rs.close();
					if (stmt != null) stmt.close();
					if (conn != null) conn.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return list;
		}
		
	// 글쓰기
	public void insert(BoardVo vo){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			
			String sql="insert into board values ( board_no_seq.nextval, ?, ?, ?, 0, SYSDATE)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getMember_no());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 글보기
	public BoardVo view(Long no){
		
		BoardVo vo=null;
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=getConnection();
			stmt=conn.createStatement();
			
			String sql=" select title, content, member_no from board where no="+no;
			rs=stmt.executeQuery(sql);
			
			if(rs.next()){
				
				vo=new BoardVo();
				
				String title=rs.getString(1);
				String content=rs.getString(2);
				Long member_no=rs.getLong(3);
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setMember_no(member_no);
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				if (rs!=null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}

	// 글삭제
	public void delete(Long no, Long member_no){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			
			String sql="delete from board where no=? and member_no=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.setLong(2, member_no);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 글수정
	public void update(BoardVo vo){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			String sql="update board set title=?, content=? where no=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	// 조회수 증가
	public void viewCount(Long no){
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			
			conn=getConnection();
			String sql="update board set view_cnt=view_cnt+1 where no=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}

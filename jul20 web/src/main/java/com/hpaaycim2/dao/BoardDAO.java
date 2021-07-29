package com.hpaaycim2.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.hpaaycim2.db.DBConnection;
import com.hpaaycim2.util.Util;

// 싱글턴 쓰는 목적?
// 객체 생성하고 싱글턴하고 차이점
// 객체 생성 목적?
public class BoardDAO {
	// 생성자 : 메소드와의 차이점은?
	private BoardDAO() {};
	//내가 객체 생성해주겠습니다.
	private static BoardDAO dao = new BoardDAO();
	
	public static BoardDAO getinstance() {
		if(dao==null) {
			dao = new BoardDAO();
		}
		return dao;
	}
	
	//전체 글 읽어오는 method()
	public ArrayList<HashMap<String, Object>> boardList(int page){
		ArrayList<HashMap<String,Object>> boardList = null;
				
	//DB접속
	//conn
	Connection con = DBConnection.dbcConnection();
	//pstmt
	PreparedStatement pstmt = null;
	//rs
	ResultSet rs = null;
	//sql -----> 테이블 새로 만들겁니다. 빈칸으로 두세요.
	//테이블 새로 만들겁니다 빈칸으로 두세요.
	String sql ="SELECT * FROM freeview LIMIT ?, 10"; //10개씩 가져오기
	//로직은 테이블명 완성 후에 짜겠습니다.
	
	try {
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, page);
		rs = pstmt.executeQuery();
		
		if(rs != null) {
			boardList = new ArrayList<HashMap<String, Object>>();
			while(rs.next()) {
				HashMap<String, Object> map = 
						new HashMap<String, Object>();
			
				map.put("totalcount", rs.getInt("totalcount"));
				map.put("commentcount", rs.getInt("commentcount"));
				map.put("fno", rs.getInt("fno"));
				map.put("ftitle", rs.getString("ftitle"));
				map.put("fcontent", rs.getString("fcontent"));
				map.put("fdate", rs.getDate("fdate"));
				map.put("fcount", rs.getInt("fcount"));
				map.put("fip", rs.getString("fip"));
				map.put("no", rs.getInt("no"));
				map.put("id", rs.getString("id"));
				map.put("name",rs.getString("name"));
				boardList.add(map);
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		Util.closeAll(rs, pstmt, con);
	}
		return boardList;
	}

	public HashMap<String, Object> detail(int fno) {
		
		Connection conn = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM freeview WHERE fno=?";
		ResultSet rs  = null;
		HashMap<String, Object> dto = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new HashMap<String, Object>();
				dto.put("total", rs.getInt("totalcount"));
				dto.put("commentcount", rs.getInt("commentcount"));
				dto.put("fno", rs.getInt("fno"));
				dto.put("ftitle", rs.getString("ftitle"));
				dto.put("fcontent",rs.getString("fcontent"));
				dto.put("fdate", rs.getDate("fdate"));
				dto.put("fcount", rs.getInt("fcount"));
				dto.put("fip", rs.getString("fip"));
				dto.put("no", rs.getInt("no"));
				dto.put("id", rs.getString("id"));
				dto.put("name", rs.getString("name"));
				dto.put("file", rs.getString("ffilename")); //파일명 불러오기
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, conn);
		}
		
		return dto;
	}

	public ArrayList<HashMap<String, Object>> commentList(int fno) {
		ArrayList<HashMap<String, Object>> commentList= null;

		Connection conn = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		String sql = "SELECT * FROM freecommentview WHERE fno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			rs= pstmt.executeQuery();
			
			if(rs != null) {
				commentList = new ArrayList<HashMap<String, Object>>();
				while(rs.next()) {
					HashMap<String, Object> map = 
							new HashMap<String, Object>();
					
					map.put("fcno", rs.getInt("fcno"));
					map.put("fno", rs.getInt("fno"));
					map.put("fccontent", rs.getString("fccontent"));
					map.put("fcdate", rs.getDate("fcdate"));
					map.put("fclike", rs.getInt("fclike"));
					map.put("fcip", rs.getString("fcip"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					commentList.add(map);
				}		
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return commentList;
	}

	public int commentModify(HashMap<String, Object> dto) {
		int result = 0;
		
		Connection con = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE freecomment SET fccontent =? WHERE fcno= ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String)dto.get("content"));
			pstmt.setInt(2, Util.str2Int((String)dto.get("fcno")));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(null, pstmt, con);
		}
		
		return result;
	}

	public int write(HashMap<String, Object> map) {
		int result = 0;
		
		Connection conn = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO free (ftitle, fcontent, fip, ffilename, no) " 
				+ "VALUES(?,?,?,?,(SELECT no FROM login WHERE id=?))";
		// 쿼리문도 준비해주세요.
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,(String)map.get("title"));
			pstmt.setString(2,(String)map.get("content"));
			pstmt.setString(3,(String)map.get("ip"));
			pstmt.setString(4,(String)map.get("file"));
			pstmt.setString(5,(String)map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(null, pstmt, conn);
		}
		
		return result;
	}

	
}







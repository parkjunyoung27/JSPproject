package com.hpaaycim2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import com.hpaaycim2.db.DBConnection;
import com.hpaaycim2.util.Util;

public class LoginDAO {
	// 생성자 : 메소드와의 차이점은?
	private LoginDAO() {};
	//내가 객체 생성해주겠습니다.
	private static LoginDAO dao = new LoginDAO();
	
	public static LoginDAO getinstance() {
		if(dao==null) {
			dao = new LoginDAO();
		}
		return dao;
	}

	public HashMap<String, Object> login(String id, String pw) {
		
		HashMap<String, Object> login = null;
		Connection conn = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id,name,grade FROM login WHERE id=? AND pw=? AND grade > 4";
		
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				login = new HashMap<String, Object>();
				login.put("id", rs.getString("id"));
				login.put("name", rs.getString("name"));
				//등급 추가
				login.put("grade", rs.getInt("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
		}finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return login;
	}

	public int idCheck(String id) {
		int result = 1;
		
		Connection conn = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM login WHERE id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, conn);
		}
				
		return result;
	}

	public static int join(HashMap<String, Object> dto) {
		int result = 0;
		
		Connection conn = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO login(name, id, pw, email, birthdate) VALUES(?, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,(String) dto.get("name"));
			pstmt.setString(2,(String) dto.get("id"));
			pstmt.setString(3,(String) dto.get("pw1"));
			pstmt.setString(4,(String) dto.get("email"));
			pstmt.setString(5, (String) dto.get("date"));
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Util.closeAll(null, pstmt, conn);
		}
		
		return result;
	}

}

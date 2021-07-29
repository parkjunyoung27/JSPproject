package com.hpaaycim2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.hpaaycim2.db.DBConnection;
import com.hpaaycim2.util.Util;

public class LogDAO {
	
	private LogDAO() {}
	private static LogDAO instance = new LogDAO();
	public static LogDAO getInstance() {
		return instance;
	}
	
	
	public static void insertLog(HashMap<String, Object> dto) {
		Connection conn = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO freelog (log_ip, log_target, log_id, log_etc) "
				+ "VALUES (?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) dto.get("ip"));
			pstmt.setString(2, (String) dto.get("target"));
			pstmt.setString(3, (String) dto.get("id"));
			pstmt.setString(4, (String) dto.get("etc"));
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}

	}


	public ArrayList<HashMap<String, Object>> loglist(int page) {
		ArrayList<HashMap<String, Object>> list = null;
		
		//DB접속
		//conn
		Connection con = DBConnection.dbcConnection();
		//pstmt
		PreparedStatement pstmt = null;
		//rs
		ResultSet rs = null;
		//sql -----> 테이블 새로 만들겁니다. 빈칸으로 두세요.
		//테이블 새로 만들겁니다 빈칸으로 두세요.
		String sql ="SELECT * FROM logview LIMIT ?, 20"; //20개씩 가져오기
		//로직은 테이블명 완성 후에 짜겠습니다.
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			
			if(rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while(rs.next()) {
					HashMap<String, Object> map = 
							new HashMap<String, Object>();
				
					map.put("log_no", rs.getInt("log_no"));
					map.put("log_ip", rs.getString("log_ip"));
					map.put("log_date", rs.getDate("log_date"));
					map.put("log_target", rs.getString("log_target"));
					map.put("log_id", rs.getString("log_id"));
					map.put("log_etc",rs.getString("log_etc"));
					map.put("totalcount", rs.getInt("totalcount"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}


	public ArrayList<String> ipList() {
		ArrayList<String> iplist = new ArrayList<String>();
		Connection con = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "SELECT DISTINCT log_ip FROM logview";
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs!= null) {
				while(rs.next()) {					
					iplist.add(rs.getString("log_ip"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		
		return iplist;
	}


	public ArrayList<String> targetList() {
		ArrayList<String> targetlist = new ArrayList<String>();
		Connection con = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "SELECT DISTINCT log_target FROM logview";
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs!= null) {
				while(rs.next()) {
					targetlist.add(rs.getString("log_target"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
			return targetlist;
	}


	public ArrayList<HashMap<String, Object>> selectIPTarget(String ip, int i) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT count(*) FROM log WHERE log_ip=?) AS totalcount"
				+ ", log_no, log_ip, log_date, log_target, log_id, log_etc"
				+ " FROM log WHERE log_ip=? LIMIT ?, 20 ";

		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, target);
			pstmt.setString(2, ip);
			pstmt.setString(3, target);
			pstmt.setString(4, ip);
			pstmt.setInt(5, i);
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	public ArrayList<HashMap<String, Object>> selectTarget(String ip, String target, int i) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT count(*) FROM log WHERE log_target=?) AS totalcount"
				+ ", log_no, log_ip, log_date, log_id, log_etc"
				+ " FROM logview WHERE log_target=? LIMIT ?, 20 ";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, target);
			pstmt.setString(2, ip);
			pstmt.setInt(3, i);
			rs = pstmt.executeQuery();
			if(rs != null) {
				list = new ArrayList<HashMap<String,Object>>();
				while(rs.next()) {
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}



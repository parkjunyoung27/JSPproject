package com.hpaaycim2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.hpaaycim2.db.DBConnection;
import com.hpaaycim2.util.Util;

public class GalleryDAO {
	//싱글턴 패턴
	private GalleryDAO() {};
	
	private  static GalleryDAO dao = new GalleryDAO(); 
	
	public static GalleryDAO getInstance() {
		if(dao == null) {
			dao = new GalleryDAO();
		}
		return dao;
	}

	public ArrayList<HashMap<String, Object>>  galleryList(int page) {
		
		ArrayList<HashMap<String, Object>> galleryList = null;
		
		Connection con = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM galleryview LIMIT ?, 6"; // 6개씩 가져오기
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			if(rs!=null) {
				galleryList = new ArrayList<HashMap<String,Object>>();
				//System.out.println("jjjj");
				while(rs.next()) {
					HashMap<String, Object> map = 
							new HashMap<String, Object>();
					//System.out.println("map-----------------");
					map.put("gno", rs.getString("gno"));
					map.put("gtitle", rs.getString("gtitle"));
					map.put("date", rs.getString("gdate"));
					map.put("gthumbnail", rs.getString("gthumbnail"));
					map.put("gcount", rs.getInt("gcount"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					map.put("totalcount", rs.getInt("totalcount"));
					galleryList.add(map);
					//System.out.println(map);
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Util.closeAll(rs, pstmt, con);
		}
		return galleryList;
	}

	public int galleryWrite(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO gallery (gtitle, gcontent, gfilename, gthumbnail, gip , no)" 
				+ "VALUES(?,?,?,?,?,(SELECT no FROM login WHERE id=?))";
		// 쿼리문도 준비해주세요.
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,(String)map.get("title"));
			pstmt.setString(2,(String)map.get("content"));
			pstmt.setString(3,(String)map.get("saveFile"));
			pstmt.setString(4,(String)map.get("thumbnail"));
			pstmt.setString(5,(String)map.get("gip"));
			pstmt.setString(6,(String)map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}

	public HashMap<String, Object> detail(int gno) {
		
		
		Connection conn = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM galleryview WHERE gno=?";
		ResultSet rs = null;
		HashMap<String, Object> list = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list = new HashMap<String, Object>();
				list.put("gno", rs.getInt("gno"));
				list.put("gtitle", rs.getString("gtitle"));
				list.put("gcontent", rs.getString("gcontent"));
				list.put("gfilename", rs.getString("gfilename"));
				list.put("gthumbnail", rs.getString("gthumbnail"));
				list.put("gip", rs.getString("gip"));
				list.put("no", rs.getInt("no"));
				list.put("gdate", rs.getString("gdate"));
				list.put("gcount", rs.getInt("gcount"));
				list.put("name", rs.getString("name"));
				list.put("id", rs.getString("id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}	
		return list;
	}

	public ArrayList<String> findFileName(HashMap<String, Object> map) {
		ArrayList<String> filename = null;
		Connection con = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT gfilename, gthumbnail FROM galleryview "
				+ "WHERE gno=? AND no=(SELECT no FROM login WHERE id=?)";
		
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Util.str2Int((String) map.get("gno")));
				pstmt.setString(2, (String)map.get("id"));
				rs = pstmt.executeQuery();
				if(rs.next()) {
					
					filename = new ArrayList<String>();
					
					String gfilename = rs.getString("gfilename");
					if(gfilename !=null && gfilename.contains(".")) {
					filename.add(gfilename);
					}else {
						filename.add(null);
					}
					
					String gthumbnail = rs.getString("gthumbnail");
					if(gthumbnail != null && gthumbnail.contains(".")) {
					filename.add(gthumbnail);
					}else {
						filename.add(null);
					}
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				Util.closeAll(rs, pstmt, con);
			}
		
		return filename;
	}

	public int del(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM gallery WHERE gno=? AND no=(SELECT no FROM login WHERE id=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int((String )map.get("gno")));
			pstmt.setString(2, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}

		return result;
	}

}



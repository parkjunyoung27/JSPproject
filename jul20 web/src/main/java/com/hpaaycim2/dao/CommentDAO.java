package com.hpaaycim2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import com.hpaaycim2.db.DBConnection;
import com.hpaaycim2.util.Util;

public class CommentDAO {
	//외부에서 생성을 못하도록 Default생성자는 private으로 선언합니다.
	private CommentDAO(){}
	//싱글턴 ㅐ턴으로 변경해 보았습니다. DBConnection과 그 형태가 같습니다.
	
	private static CommentDAO instance = new CommentDAO();
	//객체 가져오기는 아래와 같이 합니다.
	
	public static CommentDAO getInstance(){
		return instance;
	}

	public int commentInput(HashMap<String,Object> map) {
		int result = 0;
		//content, fno, id 값 넘어옴 
		String content = (String)map.get("content");
		String id = (String)map.get("id");
		int fno = (int)map.get("fno");
		
		Connection con = DBConnection.dbcConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO freecomment (fno,fccontent,no) "
				+ "VALUES (?,?,(SELECT no FROM login WHERE id=?))";		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fno);
			pstmt.setString(2, content);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(null, pstmt, con);
		}
		
		return result;
	}

}

package com.hpaaycim2.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection dbcConnection() {
		Connection conn = null;
		String url = "jdbc:mariadb://localhost:3306/hpaaycim2"; 
		//신림: jdbc:mariadb://220.70.33.29:3306/hpaaycim2
		//로컬: jdbc::mariadb//localhost:3306/hpaaycim2
		//기본적인 포트번호는 3306번이다
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url,"hpaaycim2","01234567"); //데이터베이스 연결
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return conn;
	}

}

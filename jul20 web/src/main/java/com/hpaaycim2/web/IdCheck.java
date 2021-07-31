package com.hpaaycim2.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.dao.LoginDAO;
import com.hpaaycim2.util.Util;


@WebServlet("/idCheck")
public class IdCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IdCheck() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = "";
		if(session.getAttribute("id")!=null) {
			id = (String)session.getAttribute("id");
		}
		//log남기기
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("ip", Util.getIP(request));
		log.put("id", id);
		log.put("target", "idCheck");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
		
		System.out.println("id 중복확인 요청 들어왔습니다.");
		
		int result = 1;
		id = request.getParameter("id");
		
		result = LoginDAO.getinstance().idCheck(id);
		System.out.println("중복된 ID의 수 : " + result);
		
		//agax에 결과값 반환
		PrintWriter pw = response.getWriter(); 
		pw.print(result);
	}

}

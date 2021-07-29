package com.hpaaycim2.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.util.Util;


@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Logout() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//로그남기기
		HttpSession session = request.getSession();
		String id = "";
		if(session.getAttribute("id")!=null) {
			id = (String)session.getAttribute("id");
		}
	
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("ip", Util.getIP(request));
		log.put("id", id);
		log.put("target", "logoout");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
				
		
		if(session.getAttribute("id") != null) {
			session.removeAttribute("id");
		}
		if(session.getAttribute("name") != null) {
			session.removeAttribute("name");
		}
		
		if(session.getAttribute("grade") != null) {
			session.removeAttribute("gallery");
		}
		//session.invalidate(); //해당 브라우저의 모든 세션 끊기
		
		//페이지 이동
		response.sendRedirect("./index");//서블릿 계열로만 보내기
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

package com.hpaaycim2.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hpaaycim2.dao.LoginDAO;


@WebServlet("/idCheck")
public class IdCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IdCheck() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("id 중복확인 요청 들어왔습니다.");
		
		int result = 1;
		String id = request.getParameter("id");
		
		result = LoginDAO.getinstance().idCheck(id);
		System.out.println("중복된 ID의 수 : " + result);
		
		//agax에 결과값 반환
		PrintWriter pw = response.getWriter(); 
		pw.print(result);
	}

}

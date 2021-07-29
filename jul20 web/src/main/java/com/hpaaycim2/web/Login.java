package com.hpaaycim2.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hpaaycim2.dao.LoginDAO;
import com.hpaaycim2.util.Util;


@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("./login.jsp");
		rd.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			//if 검사를 넣어주세요
			//System.out.println("로그인 시도 : " + id + " : " + pw); // 정상작동
			
			LoginDAO dao = LoginDAO.getinstance();
			
			HashMap<String, Object> member = dao.login(id, pw);
			//System.out.println("로그인 배열 생성" + member);
			//출력문 제거 - 속도저하의 원인
			//이클립스의 노란색 마크 안뜨게 하는게 좋음 
			
			//아이디 비번이 일치한다면
			if(member != null ) {
				//세션만들기 - 서버에 저장됨 
				//해당브라우저로 로그인한 사용자의 ID, name을 올린다.
				HttpSession session = request.getSession();
				session.setAttribute("id", member.get("id"));
				session.setAttribute("name", member.get("name"));
				if(Util.str2Int2((String)member.get("grade")) == 9) {					
					session.setAttribute("grade", member.get("grade"));
				}
				
				//만약 9등급이라면 세션을 만듬 
				
				//테스트
				//System.out.println(session.getAttribute("id"));
				//System.out.println(session.getAttribute("name"));
				
				response.sendRedirect("./index");
			}else {
				//아이디나 비번이 틀리다면
				response.sendRedirect("./login");
			}
			
	}

}

/*
1.servelt -> login(Login)
get 화면보여주기
	login.jsp보여주기
	
post  데이터베이스로 값 보내기
	  진짜 있는 사람? 
	  세션 만들어주기
*/
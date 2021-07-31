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

import com.hpaaycim2.dao.BoardDAO;
import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.dao.LoginDAO;
import com.hpaaycim2.util.Util;


@WebServlet("/join")
public class Join extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Join() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = "";
		if(session.getAttribute("id")!=null) {
			id = (String)session.getAttribute("id");
		}
		//log남기기
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("ip", Util.getIP(request));
		log.put("id", id);
		log.put("target", "join");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
		
		RequestDispatcher rd = request.getRequestDispatcher("./join.jsp"); // index.jsp가 열리면서 해당 내용이 뜸 
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//if 모두 들어온다면 다음 작업으로
		if(request.getParameter("id") != null
			&& request.getParameter("name") != null
			&& request.getParameter("pw1") != null
			&& request.getParameter("email") != null
			&& request.getParameter("date2") != null
			&& request.getParameter("pw1").equals(request.getParameter("pw2"))) {
			
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String pw1 = request.getParameter("pw1");
			String email = request.getParameter("email");
			String date = request.getParameter("date2");
			
			HashMap<String, Object> dto = new HashMap<String, Object>();
			dto.put("id", id);
			dto.put("name", name);
			dto.put("pw1", pw1);
			dto.put("email", email);
			dto.put("date", date);
			
			int result = LoginDAO.join(dto); 
			System.out.println(result + "개 가입완료!!!");
			
			RequestDispatcher rd = request.getRequestDispatcher("./joinResult.jsp");
			request.setAttribute("result", result);
			request.setAttribute("dto", dto);
			
			rd.forward(request, response);
			
		}else {
			response.sendRedirect("error.jsp?error=5985");
		}
			
	}

}

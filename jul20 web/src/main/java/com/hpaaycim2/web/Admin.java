package com.hpaaycim2.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.util.Util;


@WebServlet("/admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LogDAO dao = LogDAO.getInstance();
       
    public Admin() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//페이징 처리
		//두개다 들어올때 하나만 들어올때 등등 처리해줘야됨 
		//where절 2개 count where절 뒤에 두개값 붙여줘야됨 
		int page= 1;
		String ip, target = null;
		ArrayList<HashMap<String, Object>> list; 
		
		if(request.getParameter("page") != null) {
			page = Util.str2Int2(request.getParameter("page"));
		}
		if(request.getParameter("ip") != null 
				&& request.getParameter("target") != null) {
			 ip = request.getParameter("ip"); 
			target = request.getParameter("target");
					list
				= dao.loglist((page-1)*20);
			
		}else if(request.getParameter("ip") != null) {
			list 
			= dao.selectIP( ip,(page-1)*10);
		}
		else if(request.getParameter("target")!=null) {
			String target = request.getParameter("target");
			list = dao.selectTarget(ip,target,(page-1)* 10);
		}	
		
		//request.getParameter("page" )
		System.out.println("page : " + page);
		
		//page

		ArrayList<HashMap<String, Object>> loglist = dao.loglist((page-1));
		request.setAttribute("list", loglist);
		
		//ipList SELECT DISTINCE log_ip FROM logview
		ArrayList<String> ipList = dao.ipList();
		//targetList SELECT 
		ArrayList<String> targetList= dao.targetList();
		
		//admin.jsp 디스패처로 연결해주세요.
		RequestDispatcher rd = request.getRequestDispatcher("./admin.jsp");
		request.setAttribute("page", page);
		request.setAttribute("ipList", ipList);
		request.setAttribute("targetList", targetList);
		request.setAttribute("ip", request.getParameter("ip"));
		request.setAttribute("target", request.getParameter("target"));
		
		System.out.println(ipList);
		System.out.println(targetList);
		
		//totalCount 
		if(loglist != null && loglist.size() > 0 ) {
			request.setAttribute("totalCount", loglist.get(0).get("totalcount"));
		}
		//session 검사 해주세요. grade가 있으면 아래 문장 실행
		//없으면 "접근할 수 없습니다"로 출력하기
		HttpSession session = request.getSession();
		
		if(request.getAttribute("grade") != null) {
		int grade = Util.str2Int2((String)session.getAttribute("grade"));
		request.setAttribute("grade", grade);
		}
		
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

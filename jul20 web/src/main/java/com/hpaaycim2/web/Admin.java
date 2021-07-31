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
		HttpSession session = request.getSession();
		String id = "";
		if(session.getAttribute("id")!=null) {
			id = (String)session.getAttribute("id");
		}
		//log남기기
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("ip", Util.getIP(request));
		log.put("id", id);
		log.put("target", "admin");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
		
		//session 검사 해주세요. grade가 있으면 아래 문장 실행
		//없으면 "접근할 수 없습니다"로 출력하기
		//admin.jsp 디스패처로 연결해주세요.
		//log내용을 가져오기
		//1.페이징 처리 꼭 해주세요.
		int page = 1;
		// 로그 리스트 
		ArrayList<HashMap<String, Object>> list = null;
		System.out.println("페이지 : " + request.getParameter("page"));
		//페이지 값이 있을때 , page값 저장
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		//첫화면 일때  ip, target 아직 설정 안할 때 
		if(request.getParameter("ip") == null 
				&& request.getParameter("target") == null) {
			list = dao.loglist((page - 1) * 20);
		}
		//ip와 target이 전체가 선택될때 
		//jul20_web/admin?ip=&target=
		else if(request.getParameter("ip") == "" 
				&& request.getParameter("target") == "") {
			list = dao.loglist((page - 1) * 20);
		//ip와 target이 null값이 아닐때 
		}else if(request.getParameter("ip") != "" 
				&& request.getParameter("target") != "") {
			String ip = request.getParameter("ip");
			String target = request.getParameter("target");
			//둘 다 들어갔을시를 계산
			list = dao.selectIpTarget(ip, target, (page - 1) * 10);
			
		//ip값만 null이 아닐때 
		}else if(request.getParameter("ip") != ""
				&& request.getParameter("target") == "") {
			String ip = request.getParameter("ip");
			list = dao.selectIP(ip, (page - 1) * 10);
		//target값만 null이 아닐때 
		}else if(request.getParameter("target") != ""
				&& request.getParameter("ip") == "") {
			String target = request.getParameter("target");
			list = dao.selectTarget(target, (page - 1) * 10);
		}
		
		//옵션에 붙을 ip목록
		//ipList    SELECT DISTINCT log_ip FROM logview
		ArrayList<String> ipList = dao.list("log_ip");
		
		//옵션에 붙을 target 목록
		//targetList   SELECT DISTINCT log_target FROM logview
		ArrayList<String> targetList = dao.list("log_target");
		
		RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
		//가져갈 값은 여기
		request.setAttribute("list", list); //로그리스트 목록
		request.setAttribute("ipList", ipList); //ip목록
		request.setAttribute("targetList", targetList); //target 목록
		request.setAttribute("ip", request.getParameter("ip")); // ip 
		request.setAttribute("target", request.getParameter("target")); //target
		//System.out.println(list);
		//System.out.println(targetList);
		//2.totalCount
		if(list != null && list.size() > 0) {
			request.setAttribute(
					"totalCount", list.get(0).get("totalcount"));
		}
		//3.page
		request.setAttribute("page", page); //페이지 보내기
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		
		String searchName = request.getParameter("searchname");
		String search = request.getParameter("search");
		//System.out.println(searchName); //search종류
		//System.out.println(search); //설치입력값
		//System.out.println(page);
		
		ArrayList<HashMap<String, Object>> list = 
				dao.search(searchName, search, page);
	
		RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
		request.setAttribute("list", list);

		//totalCount
		if(list != null && list.size() > 0) {
			request.setAttribute(
			"totalCount", list.get(0).get("totalcount"));
		}
		//System.out.println(list.get(0).get("totalcount"));
		
		
		request.setAttribute("page", page);
		//totalCount
		//page도 보내야 합니다.
		rd.forward(request, response);
	}

}


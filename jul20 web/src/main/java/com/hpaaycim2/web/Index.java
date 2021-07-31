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

import com.hpaaycim2.dao.BoardDAO;
import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.util.Util;

@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Index() {
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
		log.put("target", "index");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);

		//paging
		int page = 1; // 기본값
		if(request.getParameter("page") != null) {
			page = Util.str2Int2(request.getParameter("page"));
		}
		
		System.out.println("page : " + request.getParameter("page"));
		
		//DB호출 -> DAO -> DTO(대신 MAP으로 바꿔서)
		//			ArrayList<BoardDTO> -> 카페24는 이 형태로 만들어야됨 
		//			ArrayLIst<HashMap<String, Object>>   -> si업체에서 주로 활용		
		
		//RD에 붙이기 
		RequestDispatcher rd = request.getRequestDispatcher("./index.jsp"); // index.jsp가 열리면서 해당 내용이 뜸 
		
		//리스트보내기
		ArrayList<HashMap<String, Object>> list = BoardDAO.getinstance().boardList((page-1)*10);		
		request.setAttribute("dto", list); //dto 객체 생성
		
		//totalcount보내기
		if(list != null && list.size() > 0) {
			request.setAttribute("totalCount", list.get(0).get("totalcount"));
	}
		//page보내기		
		request.setAttribute("page", page);
		rd.forward(request, response);
		//index.jsp 호출
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

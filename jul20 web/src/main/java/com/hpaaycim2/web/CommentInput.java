package com.hpaaycim2.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hpaaycim2.dao.CommentDAO;
import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.util.Util;


@WebServlet("/commentInput")
public class CommentInput extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public CommentInput() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글처리
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//log남기기
		HttpSession session = request.getSession();
		String logid = "";
		
		if(session.getAttribute("id")!=null) {
			logid = (String)session.getAttribute("id");
		}
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("ip", Util.getIP(request));
		log.put("id", logid);
		log.put("target", "commentInput");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);

		
		System.out.println("처음 적은 댓글 : " + request.getParameter("content"));
		System.out.println("게시판 번호 : " + request.getParameter("fno"));

		//먼저 모든 값이 들어오는지 검사
		//content fno 세션
		if(request.getParameter("content") != null
				&& request.getParameter("fno") != null
				&& (Util.str2Int2(request.getParameter("fno")) != 0)
				&& session.getAttribute("id") != null
				&& session.getAttribute("name") !=null) {
				
		
			String content = Util.replace((String)request.getParameter("content"));
			int fno = Util.str2Int(request.getParameter("fno"));
			String id = (String) session.getAttribute("id");
			//값을 다 정리했습니다. 이걸 데이터베이스로 보내주세요.
						
			//Map
			HashMap<String,Object> map = new HashMap<String, Object>();	
			
			map.put("content", content);
			map.put("fno", fno);
			map.put("id", id);
			
			//CommentDAO
			//int count = commentInput(map);
			int count = CommentDAO.getInstance().commentInput(map);
			
			//페이지 이동
			response.sendRedirect("./detail?fno="+fno);

		}else {
			response.sendRedirect("./error?code=commentInsertError");
		}
	}

}

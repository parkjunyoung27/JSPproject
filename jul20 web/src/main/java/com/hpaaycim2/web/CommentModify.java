package com.hpaaycim2.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hpaaycim2.dao.BoardDAO;
import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.util.Util;


@WebServlet("/commentModify")
public class CommentModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CommentModify() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//한글처리
		request.setCharacterEncoding("UTF-8");
		
		//log남기기
		HttpSession session = request.getSession();
		String id = "";
		if(session.getAttribute("id")!=null) {
			id = (String)session.getAttribute("id");
		}
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("ip", Util.getIP(request));
		log.put("id", id);
		log.put("target", "commentModify");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);

		System.out.println("수정내용:" + request.getParameter("content"));
		System.out.println("게시판번호" + request.getParameter("fno"));
		System.out.println("회원번호" + request.getParameter("fcno"));
		
		HashMap<String, Object> dto = new HashMap<String, Object>();
		dto.put("content", request.getParameter("content"));
		dto.put("fno", request.getParameter("fno"));
		dto.put("fcno", request.getParameter("fcno"));

		
		//DAO
		BoardDAO dao = BoardDAO.getinstance();
		int result = dao.commentModify(dto);
		System.out.println("댓글 수정상태 : " + result);
		if(result == 1) {
			response.sendRedirect("./detail?fno="
						+request.getParameter("fno"));
		} else {
			response.sendRedirect("./error?code=1234&fno="
					+request.getParameter("fno"));			
		}
		
	}

}










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


@WebServlet("/detail")
public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Detail() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("게시판 번호 : " + request.getParameter("fno"));
		
		//로그남기기
		HttpSession session = request.getSession();
		String id = "";
		if(session.getAttribute("id")!=null) {
			id = (String)session.getAttribute("id");
		}
	
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("ip", Util.getIP(request));
		log.put("id", id);
		log.put("target", "freedteail");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
		
		//값 찍히면 if문으로 값이 있을때와 없을때를 구분해주세요.
		if(request.getParameter("fno") != null
				&& Util.str2Int2(request.getParameter("fno")) !=0 ) {
			//데이터베이스에 질의 DAO
			//페이지 이동 ./detail.jsp
			int fno = Util.str2Int2(request.getParameter("fno"));
			
			
			BoardDAO dao = BoardDAO.getinstance();
			
			HashMap<String, Object> dto = dao.detail(fno);
		
			
			//혹시 댓글이 0 보다 크다면 댓글도 불러와야 합니다.
			//혹시 댓글이 0보다 큰지 작은지 어떻게 알까요?
			System.out.println("댓글 갯수 : " + dto.get("commentcount"));
			if(((int)dto.get("commentcount")) > 0) {
				//System.out.println(dto.get("commentcount"));
				//있는지 확인 되었으면 서버에 질의하기 DAO
				ArrayList<HashMap<String, Object>> commentList = dao.commentList(fno);
				//디스패쳐가 가져갈 수 있도록 붙이기
				request.setAttribute("commentList", commentList);
				//System.out.println(commentList.size());
			}
			
			//RD에 붙여넣기 dto여기에 담아주세요. 
			RequestDispatcher rd = request.getRequestDispatcher("./freedetail.jsp");
			request.setAttribute("dto", dto);
			rd.forward(request, response);
		}else {
			response.sendRedirect("./index");
			//우리는 이제 페이지 이동은 서블릿 = controller
			//					DB = Model, 
			//					화면 jsp = view
			//mvc 모델 
			//model1 model2 mvc 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

























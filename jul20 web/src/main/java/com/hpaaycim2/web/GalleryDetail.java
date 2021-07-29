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

import com.hpaaycim2.dao.GalleryDAO;
import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.util.Util;


@WebServlet("/galleryDetail")
public class GalleryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GalleryDetail() {
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
		log.put("target", "gallerydetail");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
		

		if(request.getParameter("gno") != null 
				&& Util.str2Int2(request.getParameter("gno")) != 0 ) {
			
		int gno = Util.str2Int2(request.getParameter("gno"));
		System.out.println("갤러리 게시판 번호 : " + gno);
		

		HashMap<String, Object> galleryList  
		= GalleryDAO.getInstance().detail(gno);
		
		RequestDispatcher rd = request.getRequestDispatcher("./galleryDetail.jsp");
		request.setAttribute("list", galleryList); //dto 객체 생성 
		rd.forward(request, response);		
		}
		else {
			response.sendRedirect("./gallery");

		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

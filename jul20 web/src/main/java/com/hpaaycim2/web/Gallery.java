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
import com.hpaaycim2.dao.GalleryDAO;
import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.util.Util;

@WebServlet("/gallery")
public class Gallery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Gallery() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//로그 남기기
		HttpSession session = request.getSession();
		String id = "";
		if(session.getAttribute("id")!=null) {
			 id = (String)session.getAttribute("id");
		}
		//log남기기
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("ip", Util.getIP(request));
		log.put("id", id);
		log.put("target", "Gallery");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
		
		//paging
		int page = 1; // 기본값
		if(request.getParameter("page") != null) {
			page = Util.str2Int2(request.getParameter("page"));
		}
		request.setAttribute("page", page);
		System.out.println("page : " + request.getParameter("page"));

		//리스트 보내기
		ArrayList<HashMap<String, Object>> list = GalleryDAO.getInstance().galleryList((page-1)*6);
		request.setAttribute("dtomm", list); //dto 객체 생성 

		//totalcount 받기
		if(list!= null && list.size() >0) {
			request.setAttribute("totalCount", list.get(0).get("totalcount"));
		}
		
		//RD에 붙이기 
		RequestDispatcher rd = request.getRequestDispatcher("./gallery.jsp");
		rd.forward(request, response);		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

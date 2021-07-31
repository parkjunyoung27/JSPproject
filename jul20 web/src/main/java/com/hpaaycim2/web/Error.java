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

import com.hpaaycim2.dao.LogDAO;
import com.hpaaycim2.util.Util;


@WebServlet("/error")
public class Error extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Error() {
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
		log.put("target", "error");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
		
		RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
		request.setAttribute("code", request.getParameter("code"));
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

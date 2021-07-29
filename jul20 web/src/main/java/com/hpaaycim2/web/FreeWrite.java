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
import com.hpaaycim2.util.Util;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/freeWrite")
public class FreeWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FreeWrite() {
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
		log.put("target", "freeWrite");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
		
		RequestDispatcher rd = request.getRequestDispatcher("./freeWrite.jsp"); //freeWrite.jsp로 보내기
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글처리
		request.setCharacterEncoding("UTF-8");
		
		//파일 업로드 방법
		//파일 업로드 1.cos 2.아파치 common 두가지 방법
		//cos방법 사용 -> cos.jar파일 필요
		String savePath = request.getServletContext().getRealPath("/"); //슬러시는 지금경로에 윗경로
		//String savePath2= request.getServletContext().getResource("/").getPath();
		System.out.println("디렉토리 : " + savePath + "upload/");
		savePath =  savePath + "upload/"; //진짜 파일 저장경로
		
		int maxSize = 1024 * 1024 * 10; //10MB 1024*1024=1mb 
		
		/* 1 byte 
		 * 1kbyte = 1024 byte 
		 * 1mbyte = 1024 kbyte = 1024 byte * 1024 byte
		 */
		
		MultipartRequest multi 
		= new MultipartRequest(
				request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy()); //이름을 겹치지 않게 방지해주는 것
				//요구, 경로, 크기, 인코딩설정, 파일 이름 저장
		
		//세션 불러오기 
		HttpSession session = request.getSession();
		
		// freewrite.jsp form에서 으로 전해짐 enctyoe="multipart/form-data"
		if((multi.getParameter("title")).length() == 0
				|| (multi.getParameter("content").length()) == 0) {
			response.sendRedirect("./freeWrite?code=4556");
			
		}else if(session.getAttribute("id") == null
					|| session.getAttribute("name") == null) {
			response.sendRedirect("./login?code=write"); //id나 이름이 없으면 로그인 페이지로
			
		}else {	
			/*1.png, 1.png 가 안되게  뒤에 이름을 바꿔줌
			 *1. request 리퀘스트
			 * 2. 파일저장 위치 
			 * 3. 업로드될 파일 크기
			 * 4. 인코딩 문자
			 * 5. 만약 파일중복처리?
			 */
			String title = multi.getParameter("title");
			String content = multi.getParameter("content");	
			String uploadFile =	null;
			String realFile = null;
			
			if(multi.getOriginalFileName("file1") != null) {
				//uploadFile = multi.getOriginalFileName("file1"); // 사용자가 보는 업로드 파일 이름
				realFile = multi.getFilesystemName("file1"); // 실제 저장 이름
			}
			
//			System.out.println(title);
//			System.out.println(content);
//			System.out.println(uploadFile);//사용자가 올릴때 이름
//			System.out.println("실제 저장명 : " + realFile);//실제 저장
			
			
			
				HashMap<String, Object> map = new HashMap<String,Object>();
				map.put("title", title);
				map.put("content", content);
				map.put("id", session.getAttribute("id"));
				map.put("ip", Util.getIP(request));
				map.put("file",realFile);
				
				BoardDAO dao = BoardDAO.getinstance();
				int result = dao.write(map);
				
				System.out.println("수정상태 : "+ result);
		
				if(result == 1) {
					//페이지 이동
					response.sendRedirect("./index");		
				}else {
					response.sendRedirect("./error?code=2599");
				}
		
			}
	}
}

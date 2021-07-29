package com.hpaaycim2.web;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class GalleryWrite
 */
@WebServlet("/galleryWrite")
public class GalleryWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GalleryWrite() {
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
		log.put("target", "galleryWrite");
		log.put("etc", request.getHeader("User-Agent"));
		LogDAO.insertLog(log);
		
		RequestDispatcher rd = request.getRequestDispatcher("./galleryWrite.jsp"); //freeWrite.jsp로 보내기
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//한글처리
			request.setCharacterEncoding("UTF-8");
				
			//세션 불러오기 
			HttpSession session = request.getSession();

			//파일 업로드 
			String path = request.getSession().getServletContext().getRealPath("/");
			//String savePath2= request.getServletContext().getResource("/").getPath();
			String realPath =  path + "upload/"; //진짜 파일 저장경로
			System.out.println("경로 : " + path);
			
			//톰캣을 가장서버를 통해 실제로 돌아가게 함 
			int maxSize = 1024 * 1024 * 10; //10MB 1024*1024=1mb 
			/* 1 byte 
			 * 1kbyte = 1024 byte 
			 * 1mbyte = 1024 kbyte = 1024 byte * 1024 byte */
			MultipartRequest multi 
				= new MultipartRequest(
					request, realPath, maxSize, "UTF-8", new DefaultFileRenamePolicy()); //이름을 겹치지 않게 방지해주는 것
					//(request, 경로, 최대용량, 언어설정, 중복시 정책)
			
				
			// galleryWrite.jsp form에서 서블릿으로 전해짐 enctyoe="multipart/form-data"
			if((multi.getParameter("title")).length() == 0   //제목이나 내용 없으면 다시 
					|| (multi.getParameter("content").length()) == 0) {
				response.sendRedirect("./galleryWrite?code=4556");
				
				}else if(session.getAttribute("id") == null //id,name 없으면 로그인 페이지로
							|| session.getAttribute("name") == null) {
					response.sendRedirect("./login?code=write"); //id나 이름이 없으면 로그인 페이지로
					
				}else {	
					/*1.png, 1.png 가 안되게  뒤에 이름을 바꿔줌
					 *1. request 리퀘스트
					 * 2. 파일저장 위치 
					 * 3. 업로드될 파일 크기
					 * 4. 인코딩 문자
					 * 5. 만약 파일중복처리?*/
					String title = multi.getParameter("title");
					String content = multi.getParameter("content");	
					String upFile =	multi.getOriginalFileName("file1"); // 실제업로드시 파일 이름
					String saveFile = multi.getFilesystemName("file1"); // 실제 서버 저장 이름(중복되지 않기 위해)				

					System.out.println("업로드 파일명 : "+ upFile);//사용자가 올릴때 이름
					System.out.println("실제 저장명 : " + saveFile);//실제 저장
					
					//썸네일 만들겠습니다.***
					
					String thumbnailPath = path + "thumbnail/"; //썸네일 경로 생성
					BufferedImage inputImg =  ImageIO.read(new File(realPath + saveFile)); // upload파일 이미지를 복사
					
					//가로세로 크기 지정
					int width = 160;
					int height = 160;
					
					//이미지 확장자 확인 
					String[] imgs = {"png", "gif", "jpg", "jpeg", "bmp"};
					
					for(String format : imgs) {
						BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
						
						Graphics2D gr2d = outputImg.createGraphics(); // 그래픽 조절 
						gr2d.drawImage(inputImg, 0, 0, width, height, null);

						File thumb = new File(thumbnailPath + saveFile);//새로운 파일 경로 저장 
						FileOutputStream fos = new FileOutputStream(thumb); // output 경로 저장 
						ImageIO.write(outputImg, format, thumb); //쓰기 
						fos.close();
					}
										
					//객체생성
						HashMap<String, Object> map = new HashMap<String,Object>();
						map.put("title", title);
						map.put("content", content);				
						map.put("saveFile", saveFile); //썸네일입니다. 변경할겁니다.
						map.put("thumbnail", saveFile); //썸네일입니다. 변경할겁니다.
						//ID값도 보내겠습니다.
						map.put("id", session.getAttribute("id"));
						map.put("gip", Util.getIP(request));
						
						GalleryDAO dao = GalleryDAO.getInstance();
						int result = dao.galleryWrite(map);
						
						System.out.println("수정상태 : "+ result);
				
						if(result == 1) {
							//페이지 이동
							response.sendRedirect("./gallery");		
						}else {
							response.sendRedirect("./error?code=2599");
						}
				
					}
			}
	

}

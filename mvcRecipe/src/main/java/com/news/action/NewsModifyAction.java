/**
 * 
 */
package com.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.news.dao.NewsDAO;
import com.news.vo.NewsVO;
import com.oreilly.servlet.MultipartRequest;
import com.util.FileUtil;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsModify.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ���� ���� ������ �޾Ƽ� ���� �����ϰ� �� ���� ���� �������� ������
 */
public class NewsModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth =(Integer)session.getAttribute("auth");
		if(mem_num == null || auth != 3) {//�α��� ���� ���� ���, ������ 3�� �ƴ� ���
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");
		NewsVO news= new NewsVO();
		int num = Integer.parseInt(request.getParameter("news_num"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		MultipartRequest multi = FileUtil.createFile(request);
		news.setNews_num(num);
		news.setNews_title(title);
		news.setNews_content(content);
		news.setNews_file(multi.getFilesystemName("filename"));
		NewsDAO dao=NewsDAO.getInstance();
		dao.updateNews(news);
		
		return "/WEB-INF/views/news/newsModify.jsp";
	}

}

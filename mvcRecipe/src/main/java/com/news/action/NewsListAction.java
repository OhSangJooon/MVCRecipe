package com.news.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.news.dao.NewsDAO;
import com.news.vo.NewsVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsListAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : �ո����� �׼� Ŭ����
 */
public class NewsListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�������� ȸ������ľ�
		HttpSession session = request.getSession();
		Integer auth =(Integer)session.getAttribute("auth");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		NewsDAO dao = NewsDAO.getInstance();
		int count = dao.getNewsCount();
		
		//������ó��
		//currentPage, count, rowCount, pageCount, url
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,20,10,"newsList.do");
		
		List<NewsVO> list =null;
		if(count > 0) {
			list = dao.getNewsList(page.getStartCount(), page.getEndCount());
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		request.setAttribute("auth",auth);
		return "/WEB-INF/views/news/newsList.jsp";
	}

}

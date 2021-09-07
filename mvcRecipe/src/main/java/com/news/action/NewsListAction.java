package com.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;

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
		
		return "/WEB-INF/views/news/newsList.jsp";
	}

}

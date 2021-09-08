package com.qnaboard.action;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.qnaboard.dao.QnaBoardDAO;
import com.util.PagingUtil;
import com.qnaboard.vo.QnaBoardVO;
import com.controller.Action;


/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : ListAction.java
 * @�ۼ���       : 2021. 9. 6. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �Խ��� ��� �׼�
 */
public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum="1";
		
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		int count = dao.getCount();
		
		//������ ó��
		//currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,20,10,"list.do");
		
		List<QnaBoardVO> list = null;
		if(count>0) {
			list = dao.getListBoard(page.getStartCount(), page.getEndCount());
		}
		
		//request�� ������ ����
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());		

		//JSP ��� ��ȯ
		return "/WEB-INF/views/qnaboard/list.jsp";
	}

}

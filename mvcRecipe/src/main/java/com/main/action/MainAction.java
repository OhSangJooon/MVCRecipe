package com.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.main.action
 * @FileName  : MainAction.java
 * @�ۼ���       : 2021. 9. 12. 
 * @�ۼ���       :
 * @���α׷� ���� : ����ȭ�� �׼� Ŭ����
 */

public class MainAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �α��� üũ
		HttpSession session = request.getSession();
		Integer mem_num = (Integer) session.getAttribute("mem_num");
		
		// ���������� ���� �� ����Ƿ�����, ����Ʈ������ �Խ��� ���� üũ�� ���� ���� �Ҵ�
		String division = request.getParameter("divison");
		
		// ���۵� ������ ���ڵ�
		request.setCharacterEncoding("utf-8");
		
		request.setAttribute("mem_num", mem_num);
		request.setAttribute("division", division);
		
		// DAO ȣ��
		RecipeDAO dao = RecipeDAO.getInstance();
		int count = dao.getRecipeCount(); // �� ���ڵ� ��
		
		// ����Ƿ�����, ����Ʈ������. �������� �Խ��� ���
		// ���� üũ�� null�� ��� ����Ƿ����� ������ ó��
		if(division == null) {
			String pageNum = request.getParameter("pageNum");
			if(pageNum == null) pageNum = "1";
			
			dao = RecipeDAO.getInstance();
			
			// ������ ó��
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 3, 5, "recipeList.do");
			
			List<RecipeVO> list = null;
			
			// �� ���ڵ尡 0�� �ƴ� ��� list�� �� ����
			if(count > 0) {
				list = dao.getTotalRecipeList(page.getStartCount(), page.getEndCount());
			}
			
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
			
		// ���� üũ�� ��õ���� ��� B������ ������ ó��
		}else if(division.equals("��õ��")) {
			String pageNum = request.getParameter("pageNum");
			if(pageNum == null) pageNum = "1";
			
			dao = RecipeDAO.getInstance();
			
			// ������ ó��
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 4, 5, "bestRecipeList.do?division=��õ��");
			
			List<RecipeVO> list = null;
			
			// �� ���ڵ尡 0�� �ƴ� ��� list�� �� ����
			if(count > 0) {
				list = dao.getRecommTotalRecipeList(page.getStartCount(), page.getEndCount());
			}
			
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
		
		// �������� ������ ó��
		}else {
			
		}
		
		// ���� JSP ��� ��ȯ
		return "/WEB-INF/views/main/main.jsp";
	}
	
}

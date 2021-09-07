package com.recipe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeSearchAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �˻� �׼� Ŭ����
 */

public class RecipeSearchAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �α��� ���� ��������
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		// ���۵� ������ ���ڵ� ó��
//		request.setCharacterEncoding("utf-8");
		
		// ���۵� ������ ��ȯ
		String search = request.getParameter("search");
		String category = request.getParameter("category");
		
		request.setAttribute("search", search);
		request.setAttribute("category", category);
		
//		if(category == null) category = "��ü";
		
		// �˻������� �Ļ��� ���
		if(category.equals("�Ļ�")) {
		
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
				
			RecipeDAO dao = RecipeDAO.getInstance();
			int count = dao.getRecipeCount(category, search);	// �˻����ǿ� ������ �� ���ڵ� ��
					
			// ������ ó��
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 6, 5,"recipeSearch.do");
					
			List<RecipeVO> list = null;
					
			// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
			if(count > 0) {
				list = dao.getSearchlRecipeList(page.getStartCount(), page.getEndCount(), category, search);
			}
					
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
					
			// ����� �����Ƿ� �̵�		
			
			return "/WEB-INF/views/recipe/recipeList.jsp";
			
			// �˻������� ������ ���
		}else if(category.equals("����")) {
		
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
				
			RecipeDAO dao = RecipeDAO.getInstance();
			int count = dao.getRecipeCount(category, search);	// �˻����ǿ� ������ �� ���ڵ� ��
					
			// ������ ó��
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 6, 5,"recipeSearch.do");
					
			List<RecipeVO> list = null;
					
			// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
			if(count > 0) {
				list = dao.getSearchlRecipeList(page.getStartCount(), page.getEndCount(), category, search);
			}
					
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
					
			// ����� �����Ƿ� �̵�		
			
			return "/WEB-INF/views/recipe/recipeList.jsp";
			
			// �˻������� ������ ���
		}else if(category.equals("����")) {
		
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
				
			RecipeDAO dao = RecipeDAO.getInstance();
			int count = dao.getRecipeCount(category, search);	// �˻����ǿ� ������ �� ���ڵ� ��
					
			// ������ ó��
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 6, 5,"recipeSearch.do");
					
			List<RecipeVO> list = null;
					
			// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
			if(count > 0) {
				list = dao.getSearchlRecipeList(page.getStartCount(), page.getEndCount(), category, search);
			}
					
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
					
			// ����� �����Ƿ� �̵�		
			
			return "/WEB-INF/views/recipe/recipeList.jsp";
			
			// �˻� ������ ��ü�� ���
		}else {
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
				
			RecipeDAO dao = RecipeDAO.getInstance();
			int count = dao.getRecipeCount(category, search);	// �˻����ǿ� ������ �� ���ڵ� ��
					
			// ������ ó��
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 6, 5,"recipeSearch.do");
					
			List<RecipeVO> list = null;
					
			// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
			if(count > 0) {
				list = dao.getSearchlRecipeList(page.getStartCount(), page.getEndCount(), category, search);
			}
					
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
					
			// ����� �����Ƿ� �̵�	
			return "/WEB-INF/views/recipe/recipeList.jsp";
		}
		
	}

}

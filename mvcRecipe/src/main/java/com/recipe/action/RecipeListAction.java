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
 * @FileName  : RecipeListAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ����� ������ �׼� Ŭ����
 */

public class RecipeListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �α��� ���� ��������
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		// �˻� ���� üũ�� ���� ���� �Ҵ�
		String search = request.getParameter("search");
		String division = request.getParameter("division");
		
		// ���۵� ������ Ÿ��
		request.setCharacterEncoding("utf-8");
		
		request.setAttribute("search", search);
		request.setAttribute("division", division);
		request.setAttribute("mem_num", mem_num);
		
		// �˻� ���� üũ null�̶�� �Ϲ� ������ ó��
		if(division == null) {
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
			
			RecipeDAO dao = RecipeDAO.getInstance();
			int count = dao.getRecipeCount();	// �� ���ڵ� ��
			
			// ������ ó��
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 4, 5,"recipeList.do");
			
			List<RecipeVO> list = null;
			
			// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
			if(count > 0) {
				list = dao.getTotalRecipeList(page.getStartCount(), page.getEndCount());
			}
			
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
			
			// ����� �����Ƿ� �̵�
			return "/WEB-INF/views/recipe/recipeList.jsp";
			
			// �˻������� ������ ���
		}else if(division.equals("����")) {
				
				String pageNum = request.getParameter("pageNum");
				if(pageNum==null) pageNum = "1";
					
				RecipeDAO dao = RecipeDAO.getInstance();
				int count = dao.getRecipeCount(division, search);	// �˻����ǿ� ������ �� ���ڵ� ��
						
				// ������ ó��
				// currentPage, count, rowCount, pageCount, url
				PagingUtil page = new PagingUtil(search,division, Integer.parseInt(pageNum), count, 4, 5,"recipeList.do");
						
				List<RecipeVO> list = null;
						
				// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
				if(count > 0) {
					list = dao.getSearchlRecipeList(page.getStartCount(), page.getEndCount(), division, search);
				}
						
				// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
				request.setAttribute("count", count);
				request.setAttribute("list", list);
				request.setAttribute("pagingHtml", page.getPagingHtml());
						
				// ����� �����Ƿ� �̵�		
				
				return "/WEB-INF/views/recipe/recipeList.jsp";
				
				// �˻������� ������ ���
			}else if(division.equals("����")) {
			
				String pageNum = request.getParameter("pageNum");
				if(pageNum==null) pageNum = "1";
					
				RecipeDAO dao = RecipeDAO.getInstance();
				int count = dao.getRecipeCount(division, search);	// �˻����ǿ� ������ �� ���ڵ� ��
						
				// ������ ó��
				// currentPage, count, rowCount, pageCount, url
				PagingUtil page = new PagingUtil(search,division,Integer.parseInt(pageNum), count, 4, 5,"recipeList.do");
						
				List<RecipeVO> list = null;
						
				// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
				if(count > 0) {
					list = dao.getSearchlRecipeList(page.getStartCount(), page.getEndCount(), division, search);
				}
						
				// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
				request.setAttribute("count", count);
				request.setAttribute("list", list);
				request.setAttribute("pagingHtml", page.getPagingHtml());
						
				// ����� �����Ƿ� �̵�		
				
				return "/WEB-INF/views/recipe/recipeList.jsp";
				
				// �˻������� �ۼ����� ���
			}else {
				String pageNum = request.getParameter("pageNum");
				if(pageNum==null) pageNum = "1";
					
				RecipeDAO dao = RecipeDAO.getInstance();
				int count = dao.getRecipeCount(division, search);	// �˻����ǿ� ������ �� ���ڵ� ��
						
				// ������ ó��
				// currentPage, count, rowCount, pageCount, url
				PagingUtil page = new PagingUtil(search,division,Integer.parseInt(pageNum), count, 4, 5,"recipeList.do?search="+search+"&category="+division);
						
				List<RecipeVO> list = null;
						
				// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
				if(count > 0) {
					list = dao.getSearchlRecipeList(page.getStartCount(), page.getEndCount(), division, search);
				}
						
				// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
				request.setAttribute("count", count);
				request.setAttribute("list", list);
				request.setAttribute("pagingHtml", page.getPagingHtml());
				request.setAttribute("division", division);
				request.setAttribute("search", search);
				// ����� �����Ƿ� �̵�	
				return "/WEB-INF/views/recipe/recipeList.jsp";
			}
	}

}

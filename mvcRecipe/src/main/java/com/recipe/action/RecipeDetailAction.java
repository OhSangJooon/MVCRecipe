package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeDetailAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ ������ �׼� �Ѱ��� ���ڵ� ó��
 */
public class RecipeDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// �� ��ȣ ��ȯ
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		RecipeDAO dao = RecipeDAO.getInstance();
		
		// ��ȸ�� ����
		
		// �Ѱ��� ���ڵ� �ޱ�
		RecipeVO recipe = dao.getRecipeBoard(board_num);
		
		// HTML�� ������� ����
		
		// HTML�� ������� �����鼭 �ٹٲ� ó��
		
		// �Ѱ��� ���ڵ� ��ȯ
		request.setAttribute("recipe", recipe);
		
		// ������ �������� �̵�
		return "/WEB-INF/views/recipe/recipeDetail.jsp";
	}

}

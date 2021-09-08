package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.StringUtil;

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
		dao.updateHitsCount(board_num);
		// �Ѱ��� ���ڵ� �ޱ�
		RecipeVO recipe = dao.getRecipeBoard(board_num);
		
		// HTML�� ������� ����
		recipe.setTitle(StringUtil.useBrNoHtml(recipe.getTitle()));
		// HTML�� ������� �����鼭 �ٹٲ� ó��
		recipe.setContent(StringUtil.useBrNoHtml(recipe.getContent()));
		
		// ���ϱ� ��ư�� �ϸ�ũ ��ư ��ȿ�� üũ�� ���� ���� ����
		int recommBtnCheck = 0;
		int bookmarkBtnCheck = 0;
		
		// �ϸ�ũ ��ȿ�� üũ 1�� ��� �ߺ� 0�ϰ�� ���ߺ�
		bookmarkBtnCheck = dao.bookmarkCheck(board_num, recipe.getMem_num());
		
		// ���ϱ� ��ư ��ȿ�� üũ 1�� ��� �ߺ� 0�� ��� ���ߺ�
		recommBtnCheck = dao.recomDuplicationCheck(board_num, recipe.getMem_num());
		
		// �Ѱ��� ���ڵ� ��ȯ
		request.setAttribute("recipe", recipe);
		
		// ���ϱ�� �ϸ�ũ �ߺ� üũ �� ��ȯ
		request.setAttribute("bookmarkBtnCheck", bookmarkBtnCheck);
		request.setAttribute("recommBtnCheck", recommBtnCheck);
		
		// ������ �������� �̵�
		return "/WEB-INF/views/recipe/recipeDetail.jsp";
	}

}

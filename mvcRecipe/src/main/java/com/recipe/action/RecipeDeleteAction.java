package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeDeleteAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �ۻ��� �׼� Ŭ����
 */

public class RecipeDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// �α��� üũ
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
				
		if(mem_num == null) { // �α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		// �α��� �� ���
		// ���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		// �۹�ȣ ��ȯ
		int board_num = Integer.parseInt(request.getParameter("board_num"));
				
		// RecipeDAO ȣ��
		RecipeDAO dao = RecipeDAO.getInstance();
		// �� ����
		dao.deleteRecipe(board_num);
				
		// JSP ��� ��ȯ
		return "/WEB-INF/views/recipe/recipeDelete.jsp";
		}
	}

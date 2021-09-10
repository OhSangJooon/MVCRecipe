package com.bestRecipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;

/**
 * @Package Name   : com.bestRecipe.action
 * @FileName  : RecipeListAction.java
 * @�ۼ���       : 2021. 9. 10. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ����Ʈ ������ �׼� Ŭ����
 */
public class RecipeListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		return "/WEB-INF/views/bestRecipe/bestRecipeList.jsp";
	}

}

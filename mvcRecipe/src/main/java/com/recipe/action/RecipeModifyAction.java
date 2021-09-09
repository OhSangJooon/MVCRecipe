package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.oreilly.servlet.MultipartRequest;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.FileUtil;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeModifyAction.java
 * @�ۼ���       : 2021. 9. 8.
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �ۼ��� �׼� Ŭ����
 */

public class RecipeModifyAction implements Action{

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
		
		MultipartRequest multi = FileUtil.createFile(request);
		
		// �ڹٺ�(VO) ����
		RecipeVO recipe = new RecipeVO();
		recipe.setBoard_num((Integer.parseInt(multi.getParameter("board_num"))));
		recipe.setCategory(multi.getParameter("category"));
		recipe.setTitle(multi.getParameter("title"));
		recipe.setContent(multi.getParameter("content"));
		recipe.setFilename(multi.getFilesystemName("filename"));
		recipe.setIp(request.getRemoteAddr());
		
		// RecipeDAO ȣ��
		RecipeDAO dao = RecipeDAO.getInstance();
		// �� ����
		dao.updateRecipe(recipe);
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/recipe/recipeModify.jsp";
	}

}

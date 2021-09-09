package com.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.recipe.vo.RecipeVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.member.action
 * @FileName  : MyBookmarkListAction.java
 * @�ۼ���       : 2021. 9. 9. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : �ϸ�ũ�� �Խñ��� ����ϱ� ���� Action
 */
public class MyBookmarkListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// �α��� ���� ��������
				HttpSession session = request.getSession();
				String id = (String)session.getAttribute("id");
				Integer mem_num = (Integer)session.getAttribute("mem_num");
						
				// ���۵� ������ Ÿ��
				request.setCharacterEncoding("utf-8");

				request.setAttribute("id", id);
				request.setAttribute("mem_num", mem_num);
						
				// �˻� ���� üũ null�̶�� �Ϲ� ������ ó��
				
				String pageNum = request.getParameter("pageNum");
				if(pageNum==null) pageNum = "1";
							
				MemberDAO dao = MemberDAO.getInstance();
				int count = dao.getRecipeCount(id);	// �� ���ڵ� ��
							
				// ������ ó��
				// currentPage, count, rowCount, pageCount, url
				PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 6, 5,"recipeList.do");
							
				List<RecipeVO> list = null;
							
				// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
				if(count > 0) {
					list = dao.getTotalRecipeList(page.getStartCount(), page.getEndCount(), id);
				}
							
				// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
				request.setAttribute("count", count);
				request.setAttribute("list", list);
				request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/member/myBookmarkList.jsp";

	}
}

/**
 * 
 */
package com.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.main.dao.MainDAO;
import com.news.vo.NewsVO;
import com.recipe.vo.RecipeVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.main.action
 * @FileName  : MainSerachAction.java
 * @�ۼ���       : 2021. 9. 13. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ���ο��� ���հ˻��Ҷ� ���� �����Դϴ�. 
 *  mainSearchList.do�� �������ּ���! 
 */
public class MainSearchAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//���۵� ������ ���ڵ�
		request.setCharacterEncoding("utf-8");
		String search = request.getParameter("search");
		//DAO ȣ��
		MainDAO dao = MainDAO.getInstance();
		// ���� �Խñ� �� üũ 
		int news_count = dao.searchNewsCount(search);
		// ���� ����Ʈ ���
		String newsPageNum = request.getParameter("newsPageNum");
		
		if(newsPageNum==null) newsPageNum ="1";
		PagingUtil page1 = new PagingUtil(search,null,Integer.parseInt(newsPageNum), news_count, 10, 7,"mainSearch.do?search="+search);
		List<NewsVO> NewsList = null;
		if(news_count > 0) {
			NewsList = dao.getNewsList(page1.getStartCount(), page1.getEndCount(),search);
		}
		// ����� ������ �Խñ� �� üũ
		int recipe_count = dao.searchRecipeCount(search);
		// ����� ������ ����Ʈ ���
		String recipePageNum = request.getParameter("recipePageNum");
		if(recipePageNum==null) recipePageNum ="1";
		PagingUtil page2 = new PagingUtil(search,null,Integer.parseInt(recipePageNum),recipe_count, 5, 10,"mainSearch.do?search="+search);
		List<RecipeVO> recipeList =null;
		if(recipe_count > 0) {
			recipeList = dao.getRecipeList(page1.getStartCount(), page1.getEndCount(),search);
		}
		// �Խñ� ���� list �Ѱ��ֱ�
		request.setAttribute("news_count",news_count );
		request.setAttribute("recipe_count",recipe_count );
		request.setAttribute("pagingHtmlNews", page1.getPagingHtml());
		request.setAttribute("pagingHtmlRecipe", page2.getPagingHtml());
		request.setAttribute("NewsList",NewsList );
		request.setAttribute("recipeList",recipeList );
		request.setAttribute("search", search);
		return "/WEB-INF/views/main/mainSearchList.jsp";
	}

}

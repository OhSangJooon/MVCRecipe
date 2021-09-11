package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.member.dao.MemberDAO;

/**
 * @Package Name   : com.member.action
 * @FileName  : StopAdminMemberAction.java
 * @�ۼ���       : 2021. 9. 11. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : �����ڰ� ȸ�� ������ �ϰ� ���ִ� Action
 */

public class StopAdminMemberAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String[] checked = request.getParameterValues("value_list");
		
		MemberDAO dao = MemberDAO.getInstance();
		
		for(int i = 0; i < checked.length; i++) {
			dao.stopAdminMember(i);
		}

		return "/WEB-INF/views/member/adminMemberView";
	}

}

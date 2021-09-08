package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : WriteFormAction.java
 * @�ۼ���       : 2021. 9. 6. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �Խ��� �� �ۼ�(��)
 */
public class QnaWriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		int mem_num = (Integer)session.getAttribute("mem_num");
		
		//�����ü ����
		MemberVO member = new MemberVO();
		MemberDAO dao = MemberDAO.getInstance();
		member = dao.getMember(mem_num);
		
		//request�� �����͸� ���
		String mem_id=member.getId();
		request.setAttribute("mem_id", mem_id);
		request.setAttribute("mem_num", mem_num);
		
		
		//JSP ��� ��ȯ
		return "/WEB-INF/views/qnaboard/qnaWriteForm.jsp";
	}

}

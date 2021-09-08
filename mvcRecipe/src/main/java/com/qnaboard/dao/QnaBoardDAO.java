package com.qnaboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.qnaboard.vo.QnaBoardVO;
import com.util.DBUtil;

public class QnaBoardDAO {
	//�̱��� ����
	private static QnaBoardDAO instance = new QnaBoardDAO();
	
	public static QnaBoardDAO getInstance() {
		return instance;
	}
	
	private QnaBoardDAO() {}
	
	//context.xml���� ���� ������ �о�鿩 Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
	private Connection getConnection()throws Exception{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe");
		return ds.getConnection();
	}
	//�ڿ�����
	private void executeClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if(rs!=null)try {rs.close();}catch(SQLException e) {}
		if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
		if(conn!=null)try {conn.close();}catch(SQLException e) {}
	}
	

	/**
	 * @Method �޼ҵ��  : write
	 * @�ۼ���     : 2021. 9. 6. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� �� �ۼ�
	 */
	public void write(QnaBoardVO qnaboard)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = getConnection();
			
			//SQL�� �ۼ�
			sql = "INSERT INTO qna_board (qna_num, qna_title, qna_content, qna_id, qna_passwd, qna_ip, qna_date) "
					+ "VALUES (qna_board_seq.nextval,?,?,?,?,?,SYSDATE)";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setString(1, qnaboard.getQna_title());
			pstmt.setString(2, qnaboard.getQna_content());
			pstmt.setString(3, qnaboard.getQna_id());
			pstmt.setString(4, qnaboard.getQna_passwd());
			pstmt.setString(5, qnaboard.getQna_ip());
			
			//SQL�� ����
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : getCount
	 * @�ۼ���     : 2021. 9. 6. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� �� ���ڵ� ��
	 */
	public int getCount()throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = getConnection();
			
			//SQL�� �ۼ�
			sql="SELECT COUNT(*) FROM qna_board";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			//SQL���� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ� ����
			executeClose(rs, pstmt, conn);
		}
		return count;
	}

	
	/**
	 * @Method �޼ҵ��  : getList
	 * @�ۼ���     : 2021. 9. 6. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� �� ���
	 */
	public List<QnaBoardVO> getListBoard(int startRow, int endRow)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaBoardVO> list = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM qna_board "
					+ "ORDER BY qna_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			//SQL���� �����ϰ� ������� ResultSet�� ����
			rs=pstmt.executeQuery();
			list = new ArrayList<QnaBoardVO>();
			while(rs.next()) {
				QnaBoardVO qnaboardVO = new QnaBoardVO();
				qnaboardVO.setQna_num(rs.getInt("qna_num"));
				qnaboardVO.setQna_title(rs.getString("qna_title"));
				qnaboardVO.setQna_id(rs.getString("qna_id"));
				qnaboardVO.setQna_date(rs.getDate("qna_date"));
				
				//�ڹٺ�(VO)�� ArrayList�� ����
				list.add(qnaboardVO);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
		
	}
	
	
	//�� ��
	
	//�� ����
	
	//�� ����
	
}

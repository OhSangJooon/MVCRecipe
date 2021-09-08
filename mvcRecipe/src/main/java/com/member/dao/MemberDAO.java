package com.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.member.vo.MemberVO;
import com.util.DBUtil;

/**
 * @Package Name   : com.member.dao
 * @FileName  : MemberDAO.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : MemberDAO
 */

public class MemberDAO {
	
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private MemberDAO() {}

	/**
	 * @Method �޼ҵ��  : insertMember
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : �ڿ뺹
	 * @Method ���� : ��� ȸ�� ����
	 */
	
	// ȸ�� ����
	public void insertMember(MemberVO member)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		
		int num = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			sql = "SELECT member_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			sql = "INSERT INTO member(mem_num, id) VALUES(?, ?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, member.getId());
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO member_detail(mem_num, name, passwd, email, phone, birthday, passkey) VALUES(?, ?, ?, ?, ?, ?, ?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, member.getName());
			pstmt3.setString(3, member.getPasswd());
			pstmt3.setString(4, member.getEmail());
			pstmt3.setString(5, member.getPhone());
			pstmt3.setString(6, member.getBirthday());
			pstmt3.setString(7, member.getPasskey());
			pstmt3.executeUpdate();
		
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, null);
		}
	}
	
	
	// ID �ߺ� üũ �� �α��� ó��
	/**
	 * @Method �޼ҵ��  : checkMember
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : �ڿ뺹
	 * @Method ���� : ���̵� �ߺ� üũ �� �α��� ó��
	 */
	
	public MemberVO checkMember(String id)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM member m LEFT OUTER JOIN member_detail d ON m.mem_num = d.mem_num WHERE m.id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setPasswd(rs.getString("passwd"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
	
	// ȸ�� �� ����
	/**
	 * @Method �޼ҵ��  : getMember
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : �ڿ뺹
	 * @Method ���� : ȸ�� �� ���� ��ȸ
	 */
	public MemberVO getMember(int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT d.mem_num, id, auth, name, passwd, email, phone, to_char(birthday, 'yyyy-MM-dd') as birthday, passkey, photo, join_date FROM member m JOIN member_detail d ON m.mem_num = d.mem_num WHERE m.mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setName(rs.getString("name"));
				member.setPasswd(rs.getString("passwd"));
				member.setEmail(rs.getString("email"));
				member.setPhone(rs.getString("phone"));
				member.setBirthday(rs.getString("birthday"));
				member.setPasskey(rs.getString("passkey"));
				member.setPhoto(rs.getString("photo"));
				member.setJoin_date(rs.getDate("join_date"));
				
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
	
	// ������ ���� ����
	/**
	 * @Method �޼ҵ��  : updateMyPhoto
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : �ڿ뺹
	 * @Method ���� : ������ ���� ����
	 */
	public void updateMyPhoto(String photo, int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE member_detail SET photo = ? WHERE mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, photo);
			pstmt.setInt(2, mem_num);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	
	// ȸ�� ���� �� ��й�ȣ ����
	
	/**
	 * @Method �޼ҵ��  : updateMember
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : �ڿ뺹
	 * @Method ���� : ȸ�� ���� �� ��й�ȣ ����
	 */
	
	public void updateMember(MemberVO member, String newPasswd)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE member_detail SET email = ?, phone = ?, passkey = ? WHERE mem_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getPasskey());
			pstmt.setInt(4, member.getMem_num());
			
			pstmt.executeUpdate();
			
			if(newPasswd != null) {
				sql = "UPDATE member_detail SET passwd = ? WHERE mem_num = ?";
				
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setString(1, newPasswd);
				pstmt2.setInt(2, member.getMem_num());
				pstmt2.executeUpdate();
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// ȸ�� Ż��
	
	/**
	 * @Method �޼ҵ��  : deleteMember
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : �ڿ뺹
	 * @Method ���� : ȸ�� Ż��
	 */
	public void deleteMember(int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "UPDATE member SET auth = 0 WHERE mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.executeUpdate();
			
			sql = "DELETE FROM member_detail WHERE mem_num = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, mem_num);
			pstmt2.executeUpdate();
			
			conn.commit();
		}catch (Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
}

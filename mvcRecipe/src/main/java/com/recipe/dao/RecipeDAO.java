package com.recipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.recipe.vo.RecipeCommendsVO;
import com.recipe.vo.RecipeVO;
import com.util.DBUtil;

public class RecipeDAO {
	// �̱��� ����
	private static RecipeDAO instance = new RecipeDAO();
	
	public static RecipeDAO getInstance() {
		return instance;
	}
	
	private RecipeDAO() {}
	
	
	/**
	 * @Method �޼ҵ��  : insertRecipe
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �۵��
	 */
	public void insertRecipe(RecipeVO recipe) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ����
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "INSERT INTO recipe_board (board_num,category,title,content,filename,ip,mem_num) VALUES (recipe_board_seq.nextval,?,?,?,?,?,?)";
						
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			// ?�� ������ ���ε�
			pstmt.setString(1, recipe.getCategory());
			pstmt.setString(2, recipe.getTitle());
			pstmt.setString(3, recipe.getContent());
			pstmt.setString(4, recipe.getFilename());
			pstmt.setString(5, recipe.getIp());
			pstmt.setInt(6, recipe.getMem_num());
						
			// SQL�� ����
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		
		}finally {
			// �ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	
	/**
	 * @Method �޼ҵ��  : getRecipeCount
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �ѷ��ڵ� �� ����
	 */
	public int getRecipeCount()throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// sql�� �ۼ�
			sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// rs�� ����� ���
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
		
		
	}
	
	
	/**
	 * @Method �޼ҵ��  : getRecipeCount
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �� ���ڵ� �� ���� �޼ҵ� �����ε� (�Ű����� ī�װ�, �˻���)
	 */
	
	public int getRecipeCount(String division, String search)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// �˻� ������ ������ ���
			if(division.equals("����")) {
				// sql�� �ۼ�
				sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num where title LIKE ?";
				
				// pstmt ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ?�� ������ ���ε�
				pstmt.setString(1, "%" + search + "%");
			
				
				// �˻� ������ ������ ���
			}else if(division.equals("����")) {
				// sql�� �ۼ�
				sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num where content LIKE ?";
				
				// pstmt ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ?�� ������ ���ε�
				pstmt.setString(1, "%" + search + "%");
			
				// �˻� ������ �ۼ����� ���
			}else {
				// sql�� �ۼ�
				sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num where id LIKE ?";
				
				// pstmt ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ?�� ������ ���ε�
				pstmt.setString(1, "%" + search + "%");
			}
			
			// rs�� ����� ���
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
		
		
	}
	
	
	
	/**
	 * @Method �޼ҵ��  : getTotalRecipeList
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ ��ü ����Ʈ ���� �޼ҵ�
	 */
	public List<RecipeVO> getTotalRecipeList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// SQL���� �ۼ�
			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.board_num desc) a) "
					+ "where rnum >=? and rnum <=?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// sql ���� �����ϰ� rs�� ����� ���
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(rs.getString("title"));
				recipe.setContent(rs.getString("content"));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setIp(rs.getString("ip"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCategory(rs.getString("category"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setId(rs.getString("id"));
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	/**
	 * @Method �޼ҵ��  : getSearchlRecipeList
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �˻����� ī�װ����� ��ġ�ϴ� ��ü �� ���� �޼ҵ�
	 */
	
	public List<RecipeVO> getSearchlRecipeList(int start, int end, String division, String search)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
				// ���ǽ���
			// SQL�� �ۼ�
			// �˻� ������ ������ ���
			if(division.equals("����")) {
				// SQL���� �ۼ�
				sql = "select * from (select a.*, rownum rnum from "
						+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.board_num desc) a) "
						+ "where rnum >=? and rnum <=? and title LIKE ? ";
				
				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ? �� ������ ���ε�
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				pstmt.setString(3, "%" + search + "%");
				
				
			// �˻������� ������ ���
			}else if(division.equals("����")){
				// SQL���� �ۼ�
				sql = "select * from (select a.*, rownum rnum from "
						+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.board_num desc) a) "
						+ "where rnum >=? and rnum <=? and content LIKE ?";
				
				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ? �� ������ ���ε�
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				pstmt.setString(3, "%" + search + "%");
			
			// �˻� ������ �ۼ����� ���
			}else {
				// SQL���� �ۼ�
				sql = "select * from (select a.*, rownum rnum from "
						+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.board_num desc) a) "
						+ "where rnum >=? and rnum <=? and id LIKE ?";
				
				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ? �� ������ ���ε�
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				pstmt.setString(3, "%" + search + "%");
			}
			// ���ǹ� ����
			
			// sql ���� �����ϰ� rs�� ����� ���
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(rs.getString("title"));
				recipe.setContent(rs.getString("content"));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setIp(rs.getString("ip"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCategory(rs.getString("category"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setId(rs.getString("id"));
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	
	/**
	 * @Method �޼ҵ��  : getRecipeBoard
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �� ������ ��� �޼ҵ�
	 */
	public RecipeVO getRecipeBoard(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecipeVO recipe = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "select * from recipe_board b join member m on b.mem_num = m.mem_num where b.board_num=?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ?�� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL���� �����ؼ� ������� resultSet�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				recipe = new RecipeVO();
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(rs.getString("title"));
				recipe.setContent(rs.getString("content"));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));	// ��õ��
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setIp(rs.getString("ip"));
				recipe.setCategory(rs.getString("category"));
				recipe.setId(rs.getString("id"));
				
				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return recipe;
	}
	
	
	// ---------------------------- ��ȸ��
	
	/**
	 * @Method �޼ҵ��  : updateHitsCount
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ��ȸ�� ������Ʈ �޼ҵ�
	 */
	public void updateHitsCount(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "UPDATE recipe_board set hits=hits+1 where board_num=?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL�� ����
			pstmt.executeUpdate();
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// ----------------------��õ
	
	/**
	 * @Method �޼ҵ��  : recomDuplicationCheck
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ȸ���� ��õ�� �ߺ� üũ �޼ҵ�
	 */
	public int recomDuplicationCheck(int board_num, int mem_num)throws Exception{
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "select count(*) from recommend where board_num = ? and mem_num = ?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);
			
			// SQL�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return result;
	}
	
	/**
	 * @Method �޼ҵ��  : recomDelete
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� :  ������ ��õ ���� �޼ҵ�
	 */
	public void recomDelete(int board_num, int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// SQL�� �ۼ�
			sql = "delete from recommend where board_num = ? and mem_num = ?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);
			
			// SQL�� ����
			pstmt.executeUpdate();
					
			// ��õ ���̺��� �����͸� �����ϸ� ������ ���̺��� ��õ���� -1 ��� �Ѵ�.
			
			//SQL�� �ۼ�
			sql = "update recipe_board set recom_count = recom_count-1 where board_num=?";
			
			// pstmt ��ü ����
			pstmt2 = conn.prepareStatement(sql);
						
			// ? �� ������ ���ε�
			pstmt2.setInt(1, board_num);
					
			// SQL�� ����
			pstmt2.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : recomAdd
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : �������� ��õ���� 1 ���������ָ鼭 ��õ���̺� �߰��ϴ� �޼ҵ�
	 */
	public void recomAdd(int board_num , int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "update recipe_board set recom_count = recom_count+1 where board_num=?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL�� ����
			pstmt.executeUpdate();
					
			// ��õ ���̺��� �����͸� �����ϸ� ������ ���̺��� ��õ���� -1 ��� �Ѵ�.
			
			//SQL�� �ۼ�
			sql = "insert into recommend (board_num, mem_num) values (?,?)";
					
			// pstmt ��ü ����
			pstmt2 = conn.prepareStatement(sql);
									
			// ? �� ������ ���ε�
			pstmt2.setInt(1, board_num);
			pstmt2.setInt(2, mem_num);			
			
			// SQL�� ����
			pstmt2.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
			DBUtil.executeClose(null, pstmt2, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : recomCount
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ���� �������� ��õ���� ��ȯ�ϴ� �޼ҵ�
	 */
	public int recomCount(int board_num)throws Exception {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "select recom_count from recipe_board where board_num = ?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("recom_count");
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	// --------------------�ϸ�ũ
	
	/**
	 * @Method �޼ҵ��  : addBookmark
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : �ϸ�ũ ���̺� �ϸ�ũ �߰� �޼ҵ�
	 */
	public void addBookmark(int board_num, int mem_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
	
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "insert into bookmark (book_num, board_num, mem_num) values (bookmark_seq.nextval,?,?)";
					
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
									
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);			
			
			// SQL�� ����
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	

	/**
	 * @Method �޼ҵ��  : deleteBookmark
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : �ϸ�ũ ����
	 */
	public void deleteBookmark(int board_num, int mem_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
	
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "delete from bookmark where board_num = ? and mem_num = ?";
					
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
									
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);			
			
			// SQL�� ����
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	/**
	 * @Method �޼ҵ��  : bookmarkCheck
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ȸ���� �̹� �ϸ�ũ�� �ߴ��� üũ
	 */
	public int bookmarkCheck(int board_num, int mem_num)throws Exception{
		int check = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "select count(*) from bookmark where board_num = ? and mem_num = ?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);
			
			// SQL�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				check = rs.getInt(1);
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return check;
	}
	
	
	
	// ------------ ��� �κ�
	
	public void insertRecipeCommend(RecipeCommendsVO recipeReply)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "insert into comments (comm_num, comm_con, mem_num, board_num) "
					+ "values (comments_seq.nextval,?,?,?)";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setString(1, recipeReply.getComm_con());
			pstmt.setInt(2, recipeReply.getMem_num());
			pstmt.setInt(3, recipeReply.getBoard_num());
			
			// SQL�� ����
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : updateRecipe
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �� �����ϱ�
	 */
	
	public void updateRecipe(RecipeVO recipe) throws Exception {
		// ���� ���� : category, title, content, filename, ip, modify_date
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ����
			conn = DBUtil.getConnection();
			
			// SQL�� �ۼ�
			sql = "UPDATE recipe_board SET category=?,title=?,content=?,filename=?,ip=?,modify_date=SYSDATE WHERE board_num=?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			// ?�� ������ ���ε�
			pstmt.setString(1, recipe.getCategory());
			pstmt.setString(2, recipe.getTitle());
			pstmt.setString(3, recipe.getContent());
			pstmt.setString(4, recipe.getFilename());
			pstmt.setString(5, recipe.getIp());
			pstmt.setInt(6, recipe.getBoard_num());
			
			// SQL�� ����
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		
		}finally {
			// �ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : recipeDelete
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �� �����ϱ�
	 */
	
	public void deleteRecipe(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ����
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "DELETE FROM recipe_board WHERE board_num=?";
			
			// PreparedStatment ��ü ����
			pstmt = conn.prepareStatement(sql);
			// ?�� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL�� ����
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		
		}finally {
			// �ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
}

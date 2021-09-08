package com.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.news.vo.NewsVO;
import com.util.DBUtil;




/**
 * @Package Name   : com.news.dao
 * @FileName  : NewsDAO.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : 
 */
public class NewsDAO {
	private static NewsDAO instance = new NewsDAO();
	
	public static NewsDAO getInstance() {
		return instance;
	}
	
	private NewsDAO() {}

	
	
	
	/**
	 * @Method �޼ҵ��  : insertNews
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �������׿� �ö� ������ ��� �ֽ��ϴ�.
	 */
	public void insertNews(NewsVO news) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
		conn = DBUtil.getConnection();
		sql="insert into NEWS_BOARD VALUES (NEWS_BOARD_SEQ.nextval, ?, ?, ? , 1, SYSDATE, SYSDATE, ?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,news.getNews_title());
		pstmt.setString(2,news.getNews_content());
		pstmt.setInt(3,news.getMem_num());
		pstmt.setString(4,news.getNews_file());
		pstmt.executeQuery();
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : getNewsCount
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �������� ����Ʈ ���� ���� �޼���
	 */
	public int getNewsCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql="SELECT COUNT(*) FROM NEWS_BOARD";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				count =rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return count;
	}
	
	/**
	 * @Method �޼ҵ��  : getNewsList
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� :�������� ����Ʈ �̴� �޼���
	 */
	public List<NewsVO> getNewsList(int start, int end) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<NewsVO> list = null;
		try {
			conn = DBUtil.getConnection();
			sql= "SELECT * FROM (SELECT a.*,rownum rnum FROM"
				+"(SELECT * FROM news_board b JOIN member_detail m ON b.mem_num = m.mem_num ORDER BY news_num DESC)a) WHERE rnum>=? AND rnum<=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
		
			rs=pstmt.executeQuery();
			list = new ArrayList<NewsVO>();
			while(rs.next()) {
				NewsVO news = new NewsVO();
				news.setNews_num(rs.getInt("news_num"));
				news.setNews_title(rs.getString("news_title"));
				news.setNews_content(rs.getString("news_content"));
				news.setname(rs.getString("name"));
				news.setNews_date(rs.getDate("news_date"));
				news.setNews_modi(rs.getDate("news_modi"));
				news.setNews_hits(rs.getInt("news_hits"));
				list.add(news);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {		
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list; 	
	}
	
	
	/**
	 * @Method �޼ҵ��  : updateCount
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : ��ȸ�� ���� 
	 */
	public void updateCount(int num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql="update news_board set news_hits = news_hits + 1 where news_num = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);			
		}	
	}
	/**
	 * @Method �޼ҵ��  : getNews
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �������� ������ Ȯ���ϴ� �޼ҵ�. ���� ������
	 */
	public NewsVO getNews(int num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NewsVO news = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql="SELECT * FROM news_board b JOIN member_detail m ON b.mem_num = m.mem_num WHERE news_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				news = new NewsVO();
				news.setNews_num(rs.getInt("news_num"));
				news.setname(rs.getString("name"));
				news.setNews_title(rs.getString("news_title"));
				news.setNews_date(rs.getDate("news_date"));
				news.setNews_modi(rs.getDate("news_modi"));
				news.setNews_content(rs.getString("news_content"));
				news.setNews_hits(rs.getInt("news_hits"));
				news.setNews_file(rs.getString("news_file"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);			
		}
		return news;
	}
	/**
	 * @Method �޼ҵ��  : updateNews
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� :���������� �����ϴ� �ڵ� ���� ����� �ΰ�ó���ص�
	 */
	public void updateNews(NewsVO news) throws Exception{
		Connection conn= null;
		PreparedStatement pstmt= null;
		String sql = null;
		ResultSet rs= null;
		
		try {
			conn=DBUtil.getConnection();
			sql="UPDATE news_board SET news_title=?, news_content=?, news_file=null, news_modi=SYSDATE WHERE news_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,news.getNews_title());
			pstmt.setString(2,news.getNews_content());
			//pstmt.setString(3,news.getNews_file());
			pstmt.setInt(3,news.getNews_num());
			rs=pstmt.executeQuery();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);			
		}
	}
	public void DeleteNews(int num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql= null;
		
		try {
			conn=DBUtil.getConnection();
			sql="delete from news_board where news_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeQuery();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);			
		}
	}
}

package edu.kh.jdbc.board.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.dto.Comment;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class CommentDAO {

	// JDBC 객체 참조 변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	// 기본 생성자 prop 에 comment-sql 연결 담아줌
	public CommentDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("comment-sql.xml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 댓글 목록 조회 SQL 수행 DAO
	 * @return commentList
	 */
	public List<Comment> selectCommentList(Connection conn, int input) throws Exception {

		List<Comment> commentList = new ArrayList<Comment>();
		
		try {
			String sql = prop.getProperty("selectCommentList");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Comment c = new Comment();
				
//				c.setCommentNo(rs.getInt("COMMENT_NO"));
//				c.setCommentContent(rs.getString("COMMENT_CONTENT"));
//				c.setMemberNo(rs.getInt("MEMBER_NO"));
//				c.setMemberName(rs.getString("MEMBER_NM"));
//				c.setCreateDate(rs.getString("CREATE_DT"));
				
				// 컬럼 순서로도 가져올 수 있음
				c.setCommentNo(rs.getInt(1));
				c.setCommentContent(rs.getString(2));
				c.setMemberNo(rs.getInt(3));
				c.setMemberName(rs.getString(4));
				c.setCreateDate(rs.getString(5));
				
				commentList.add(c); // 리스트에 추가
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return commentList;
	}

	/** 댓글 등록 SQL 수행 DAO
	 * @param conn
	 * @param boardNo
	 * @param commentContent
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public int insertComment(Connection conn, int boardNo, String commentContent, int memberNo) throws Exception {

		int result = 0;
		
		try {
			String sql = prop.getProperty("insertComment");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, commentContent);
			pstmt.setInt(2, memberNo);
			pstmt.setInt(3, boardNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 댓글 확인 SQL 실행 DAO
	 * @param conn
	 * @param commentNo
	 * @param boardNo
	 * @param memberNo
	 * @return check
	 */
	public int checkCommentNo(Connection conn, int commentNo, int boardNo, int memberNo) throws Exception {
		
		int check = 0;
		
		try {
			String sql = prop.getProperty("checkCommentNo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, commentNo);
			pstmt.setInt(2, memberNo);
			pstmt.setInt(3, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) check = rs.getInt(1);
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return check;
	}

	/** 댓글 수정 SQL 수행 DAO
	 * @param conn
	 * @param commentNo
	 * @param commentContent
	 * @return result
	 * @throws Exception
	 */
	public int updateComment(Connection conn, int commentNo, String commentContent) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateComment");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, commentContent);
			pstmt.setInt(2, commentNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 댓글 삭제 SQL 수행 DAO(UPDATE)
	 * @param conn
	 * @param commentNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteComment(Connection conn, int commentNo) throws Exception {

		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteComment");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, commentNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

}

package edu.kh.jdbc.board.model.service;

import java.sql.Connection;

import edu.kh.jdbc.board.model.dao.CommentDAO;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class CommentService {

	private CommentDAO dao = new CommentDAO();

	/** 댓글 등록 서비스
	 * @param boardNo
	 * @param string
	 * @param memberNo
	 * @return result
	 */
	public int insertComment(int boardNo, String commentContent, int memberNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.insertComment(conn, boardNo, commentContent, memberNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 댓글 확인 서비스
	 * @param commentNo
	 * @param boardNo
	 * @param memberNo
	 * @return check
	 * @throws Exception
	 */
	public int checkCommentNo(int commentNo, int boardNo, int memberNo) throws Exception {
		Connection conn = getConnection();
		
		int check = dao.checkCommentNo(conn, commentNo, boardNo, memberNo);
		
		// SELECT COUNT(*) 할 거라서 트랜잭션 제어 해주지 않아도 됨
		
		close(conn);
		
		return check;
	}

	/** 댓글 수정 서비스
	 * @param commentNo
	 * @param commentContent
	 * @return result
	 * @throws Exception
	 */
	public int updateComment(int commentNo, String commentContent) throws Exception {

		Connection conn = getConnection();
		
		int result = dao.updateComment(conn, commentNo, commentContent);
		
		// 트랜잭션 제어 처리
		if(result > 0) commit(conn);
		else rollback(conn);
		
		// Connection 반환
		close(conn);
		
		return result;
	}

	/** 댓글 삭제 서비스
	 * @param commentNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteComment(int commentNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteComment(conn, commentNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}
}

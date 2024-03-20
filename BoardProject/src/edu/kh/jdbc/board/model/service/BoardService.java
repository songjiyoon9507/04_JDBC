package edu.kh.jdbc.board.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.dto.Board;
import edu.kh.jdbc.board.model.dto.Comment;

import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardService {
	
	private BoardDAO dao = new BoardDAO();

	private CommentDAO commentDao = new CommentDAO();
	
	/** 게시글 목록 조회 서비스
	 * @return boardList
	 */
	public List<Board> selectAllBoard() throws Exception {

		Connection conn = getConnection();
		
		List<Board> boardList = dao.selectAllBoard(conn);
		
		close(conn);
		
		return boardList;
	}

	/** 게시글 상세 조회 서비스
	 * @param input
	 * @param memberNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(int input, int memberNo) throws Exception {
		
		// 1. 커넥션 생성
		Connection conn = getConnection();
		
		// 2. 게시글 상세 조회 DAO 메서드 호출
		Board board = dao.selectBoard(conn, input);
		
		// 3. 게시글이 조회된 경우
		if(board != null) {
			
			// *******************************************************************
			// ** 해당 게시글에 대한 댓글 목록 조회 DAO 호출 **
			List<Comment> commentList = commentDao.selectCommentList(conn, input);
			
			// board 에 댓글 목록 세팅
			board.setCommentList(commentList);
			// *******************************************************************
			
			// 4. 조회수 증가
			// 단, 게시글 작성자와 로그인한 회원이 다를 경우에만 증가
			if(board.getMemberNo() != memberNo) {
				// 조회한 게시글 작성한 회원번호 != 로그인한 회원번호
				
				// 5. 조회 수 증가 DAO 메서드 호출 (UPDATE)
				int result = dao.updateReadCount(conn, input);
				
				// 6. 트랜잭션 제어 처리 + 데이터 동기화 처리
				if(result > 0) {
					commit(conn);
					
					// 현재 조회하고 있는 board 의 readCount + 1
					// 조회된 board 의 조회수 0
					// DB의 조회수는 1
					// -> 조회 결과인 board 의 조회수도 1 증가
					board.setReadCount(board.getReadCount() + 1);
					
				} else rollback(conn);
			}
		} 
		
		// 7. 커넥션 반환
		close(conn);
		
		return board;
	}

	/** 게시글 수정
	 * @param boardTitle
	 * @param string
	 * @param boardNo
	 * @return result
	 */
	public int updateBoard(String boardTitle, String boardContent, int boardNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateBoard(conn, boardTitle, boardContent, boardNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 게시글 삽입 서비스
	 * @param boardTitle
	 * @param string
	 * @param memberNo
	 * @return result
	 */
	public int insertBoard(String boardTitle, String boardContent, int memberNo) throws Exception {
		
		Connection conn = getConnection();
		
		// 다음 게시글 번호 생성 dao 호출
		int boardNo = dao.nextBoardNo(conn);
		// 실제 웹사이트 서비스
		// 이용자 5만명 지금 시간 게시글 쓰는 사람 INSERT 구문이 동시에 여러 명 일어날 수 있음
		// 시퀀스 값이 의도치 않게 많이 늘어날 수 있음
		// title, content 게시글 삽입 + 이미지 삽입 images
		// 게시글 삽입할 때 시퀀스 안 구해놓고 insert 구문에 바로 발급해서 넣으면
		// title,content 내용은 1번 시퀀스 이미지 삽입할 때 다른 사람이 게시글 쓰면
		// image 넣을 때 다른 시퀀스 발생할 수도 있음
		// 둘의 SQL 이 다르기 때문에 시간 사이에 다른 시퀀스 발생할 수 있어서
		// 먼저 시퀀스 구해놓고 갖다 씀
		
		// 제목, 내용, 회원번호 + 다음 게시글 번호
		int result = dao.insertBoard(conn, boardTitle, boardContent, memberNo, boardNo);
		
		// 트랜잭션 제어
		if(result > 0) {
			commit(conn);
			result = boardNo; // insert 삽입 성공 시 현재 삽입된 게시글 번호를 반환
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		// 삽입 성공 시 현재 삽입된 게시글 번호
		// 실패 시 0
		return result;
	}

	/** 게시글 삭제 서비스
	 * @param boardNo
	 * @return result
	 */
	public int deleteBoard(int boardNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteBoard(conn, boardNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}
}

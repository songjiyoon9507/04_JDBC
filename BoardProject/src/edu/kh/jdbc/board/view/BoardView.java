package edu.kh.jdbc.board.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.dto.Board;
import edu.kh.jdbc.board.model.dto.Comment;
import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.common.Session;

public class BoardView {

	private Scanner sc = new Scanner(System.in);
	
	private BoardService boardService = new BoardService();
	
	// 댓글 화면 출력 객체
	private CommentView commentView = new CommentView();
	
	public void boardMenu() {
		
		int input = -1;
		
		do {
			
			try {
				System.out.println("\n===== 게시판 기능 =====\n");
				System.out.println("1. 게시글 목록 조회");
				System.out.println("2. 게시글 상세 조회(+ 댓글 기능)");
				System.out.println("3. 게시글 작성");
				// 제목, 내용(StringBuffer 이용) 입력
				// -> 게시글 삽입 서비스(제목, 내용, 로그인 회원 번호) 호출
				
				System.out.println("9. 메인 메뉴로 돌아가기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("\n메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				System.out.println();
				
				switch(input) {
				case 1: selectAllBoard();  break; // 게시글 목록 조회
				case 2: selectBoard(); break; // 게시글 상세 조회
				case 3: insertBoard(); break; // 게시글 등록(삽입)
				
				case 9:
					System.out.println("\n===== 메인 메뉴로 돌아갑니다 =====\n");
					break;
				
				case 0:
					System.out.println("\n=== 프로그램 종료 ===\n");
					System.exit(0);
				default: System.out.println("\n*** 메뉴 번호만 입력 해주세요 ***\n"); 
				}
				
				System.out.println();
				
			} catch (InputMismatchException e) {
				System.out.println("\n*** 입력 형식이 올바르지 않습니다***\n");
				sc.nextLine(); // 입력버퍼에 잘못된 문자열 제거
				input = -1; // while문 종료 방지
			}
			
		}while(input != 9);
		
	}

	/**
	 * 게시글 목록 조회
	 */
	public void selectAllBoard() {
		System.out.println("\n=== 게시글 목록 조회 ===\n");
		
		// 3 | 샘플 제목[2] | 신짱구 | 2024-03-19 | 0
		// 3 | 샘플 제목[2] | 신짱구 | 2024-03-19 | 0
		// 3 | 샘플 제목[2] | 신짱구 | 2024-03-19 | 0
		// ...
		
		try {
			// 게시글 목록 조회 서비스 호출
			List<Board> boardList = boardService.selectAllBoard();
			
			// 게시글이 없는 경우
			if(boardList.isEmpty()) {
				System.out.println("\n*** 게시글이 존재하지 않습니다. ***\n");
				return;
			}
			
//			for(int i = 0 ; i < boardList.size() ; i++) {
//				
//				System.out.printf("%d | %s[%d] | %s | %s | %d\n",
//						boardList.get(i).getBoardNo(),
//						boardList.get(i).getBoardTitle(),
//						boardList.get(i).getCommentCount(),
//						boardList.get(i).getMemberName(),
//						boardList.get(i).getCreateDate(),
//						boardList.get(i).getReadCount());
//			}
			
			// 향상된 for문
			for(Board b : boardList) {
				System.out.printf("%d | %s[%d] | %s | %s | %d \n",
						b.getBoardNo(),
						b.getBoardTitle(),
						b.getCommentCount(),
						b.getMemberName(),
						b.getCreateDate(),
						b.getReadCount()
						);
			}
			
		} catch (Exception e) {
			System.out.println("\n*** 게시글 목록 조회 중 예외 발생 ***\n");
			e.printStackTrace();
		}
		
	}

	/**
	 * 게시글 상세 조회 + 댓글 목록 조회
	 */
	public void selectBoard() {
		System.out.println("\n=== 게시글 상세 조회 ===\n");
		
		// 게시글 번호 입력
		// 1) 번호가 일치하는 게시글이 있으면 조회
		// -> 조회수 증가(단, 자신이 작성한 게시글인 경우 조회수 증가 X)
		// -> 자신이 작성한 게시글일 경우
		//    수정/삭제 가능하도록 기능 노출
		// + 댓글 목록/댓글 기능 추가 예정
		
		// 2) 번호가 일치하는 게시글 없으면
		// -> 해당 게시글이 존재하지 않습니다.
		
		System.out.print("게시글 번호 입력 : ");
		int input = sc.nextInt();
		sc.nextLine(); // 입력 버퍼 개행 문자 제거
		
		// 게시글 상세 조회 서비스 호출
		try {
			// 하나의 게시글 조회
			Board board = boardService.selectBoard(input, Session.loginMember.getMemberNo());
			// 입력받은 게시글 번호, 로그인한 회원 번호 (조회수 증가 사용)
			if(board == null) { // 조회된 결과가 없을 때
				System.out.println("\n*** 해당 게시글이 존재하지 않습니다. ***\n");
				return;
			}
			
			// 상세 조회된 거 보여줄 거
			System.out.println("--------------------------------------------------------");
			System.out.printf("글번호 : %d \n제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
			System.out.printf("작성자 : %s | 작성일 : %s  \n조회수 : %d\n",
					board.getMemberName(), board.getCreateDate(), board.getReadCount());
			System.out.println("--------------------------------------------------------\n");
			System.out.println(board.getBoardContent());
			System.out.println("\n--------------------------------------------------------");
			
			// ******************************************************************************
			/* 해당 게시글의 댓글 목록 조회 */
			if(!board.getCommentList().isEmpty()) { // 댓글 목록이 있다면
				
				for(Comment c : board.getCommentList()) {
					System.out.printf("[댓글 번호 : %d] 작성자 : %s 작성일 : %s\n%s\n",
							c.getCommentNo(),
							c.getMemberName(),
							c.getCreateDate(),
							c.getCommentContent());
					System.out.println("--------------------------------------------------------");
				}
				
			}
			
			/* 댓글 메뉴 출력 */
			// 1) 댓글 등록 - 누가 몇 번 게시글에 작성하는가?
			// 2) 댓글 수정 - 누가 몇 번 게시글에 있는 몇 번 댓글을 수정할 것인가?
			// 3) 댓글 삭제 - 누가 몇 번 게시글에 있는 몇 번 댓글을 삭제할 것인가?
			
			commentView.commentMenu(input); // 게시글 번호 전달
			
			// *******************************************************************
			
			// commentMenu 댓글 기능 종료해야 아래 기능 나옴
			
			// 로그인한 회원이 작성한 게시글일 경우
			// 게시글 수정/삭제 기능 노출
			
			if(Session.loginMember.getMemberNo() == board.getMemberNo()) {
				
				while(true) {
					System.out.println("\n=== [내가 쓴 게시글] ===\n");
					System.out.println("1) 수정");
					System.out.println("2) 삭제");
					System.out.println("0) 게시판 메뉴로 돌아가기");
					
					System.out.print("선택 >> ");
					input = sc.nextInt();
					sc.nextLine();
					
					// 수정/삭제 기능 수행 후 게시판 메뉴로 돌아가기
					switch(input) {
					case 1 : updateBoard(board.getBoardNo()); return;
					// 게시글 번호를 매개변수로 전달 - 해당 번호의 게시글을 수정
					case 2 : deleteBoard(board.getBoardNo()); return;
					case 0 : return;
					default : System.out.println("\n*** 잘못 입력 하셨습니다. ***\n");
					}
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("\n*** 게시글 상세 조회 중 예외 발생 ***\n");
			e.printStackTrace();
		}
		
	}

	/** 게시글 수정
	 * @param boardNo
	 */
	public void updateBoard(int boardNo) {
		System.out.println("\n=== 게시글 수정 ===\n");
		
		System.out.print("수정할 제목 : ");
		String boardTitle = sc.nextLine();
		
		StringBuffer sb = new StringBuffer();
		
		System.out.println("[수정할 내용 입력] <!wq 입력 시 종료>");
		
		while(true) {
			String str = sc.nextLine();
			
			if(str.equals("!wq")) break;
			
			sb.append(str);
			sb.append("\n");
			
		}
		
		try {
			// 게시글 수정 서비스 호출
			int result = boardService.updateBoard(boardTitle, sb.toString(), boardNo);
			
			if(result > 0) {
				System.out.println("\n=== 게시글이 수정 되었습니다. ===\n");
			} else {
				System.out.println("\n*** 수정 실패 ***\n");
			}
			
		} catch(Exception e) {
			System.out.println("\n*** 게시글 수정 중 예외 발생 ***\n");
			e.printStackTrace();
		}
		
	}

	/** 게시글 삭제 기능
	 * @param boardNo
	 */
	public void deleteBoard(int boardNo) {
		try {
			System.out.println("\n[게시글 삭제]\n");
			
			while(true) {
				System.out.print("정말로 삭제하시겠습니까?(Y/N) : ");
				char answer = sc.next().toUpperCase().charAt(0);
				
				if(answer == 'N') {
					System.out.println("[삭제 취소]");
					return;
				}
				
				if(answer != 'Y') {
					System.out.println("[잘못 입력하셨습니다.]");
					continue;
				}
				
				break;
			}
			
			int result = boardService.deleteBoard(boardNo);
			
			if(result > 0) {
				System.out.println("\n=== 게시글이 삭제 되었습니다. ===\n");
			} else {
				System.out.println("\n*** 게시글 삭제 실패 ***\n");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 게시글 등록(INSERT)
	 */
	public void insertBoard() {
		System.out.println("\n===== 게시글 등록 =====\n");
		
		// 제목 입력
		System.out.print("제목 입력 : ");
		String boardTitle = sc.nextLine();
		
		// 내용 입력(StringBuffer)
		StringBuffer sb = new StringBuffer();
		
		System.out.println("[등록할 내용 입력] <!wq 입력 시 종료>");
		
		while(true) {
			String str = sc.nextLine();
			
			if(str.equals("!wq")) break;
			
			sb.append(str);
			sb.append("\n");
		}
		
		try {
			// 게시글 삽입 서비스 호출
			int result = boardService.insertBoard(boardTitle, sb.toString(), Session.loginMember.getMemberNo());
			
			// + 이미지 삽입 (게시글 번호, 이미지 경로...)
			
			if(result > 0) {
				// 게시글 등록 성공
				// result 에 게시글 번호 들어와있음
				System.out.println("\n=== 등록 되었습니다. ====\n");
				
				// 등록된 게시글 상세 조회 서비스 호출
				// -> 게시글 번호, 로그인 회원 번호
				Board board = boardService.selectBoard(result, Session.loginMember.getMemberNo());
													  // 등록된 게시글 번호, 회원 번호
				
				System.out.println("--------------------------------------------------------");
				System.out.printf("글번호 : %d \n제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
				System.out.printf("작성자 : %s | 작성일 : %s  \n조회수 : %d\n",
						board.getMemberName(), board.getCreateDate(), board.getReadCount());
				System.out.println("--------------------------------------------------------\n");
				System.out.println(board.getBoardContent());
				System.out.println("\n--------------------------------------------------------");
				
			} else {
				// 게시글 등록 실패
				System.out.println("\n*** 게시글 등록 실패 ***\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n*** 게시글 등록 중 예외발생 ***\n");
			e.printStackTrace();
		}
		
	}
}

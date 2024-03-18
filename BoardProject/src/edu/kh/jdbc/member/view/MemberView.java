package edu.kh.jdbc.member.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.member.model.dto.Member;
import edu.kh.jdbc.member.model.service.MemberService;

/** 회원 전용 화면
 * 
 */
public class MemberView {
	
	private Scanner sc = new Scanner(System.in);
	
	private MemberService service = new MemberService();
	
	/**
	 * 회원 기능 메뉴 View
	 */
	public void memberMenu() {
		int input = 0;
		
		do {
			try {
				System.out.println("\n=== 회원 기능 ===\n");
				
				System.out.println("1. 내 정보 조회");
				System.out.println("2. 회원 목록 조회(아이디, 이름, 성별)");
				System.out.println("3. 내 정보 수정(이름, 성별, (현재 로그인한 회원번호))");
				// 지금 로그인한 사람 정보 수정
				// DB 업데이트 하면 loginMember 도 업데이트 해줘야함
				System.out.println("4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");
				System.out.println("5. 회원 탈퇴(보안코드, 비밀번호, UPDATE)");
				
				System.out.println("9. 메인 메뉴로 돌아가기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("\n메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 입력 버퍼 개행 제거
				
				switch(input) {
				case 1 : selectMyInfo(); break;
				case 2 : selectMemberList(); break;
				case 3 : updateMember(); break;
//				case 4 : updatePassword(); break;
//				case 5 : if( unRegisterMenu() ) return; break;
				// unRegisterMenu 호출하면 반환 받는 값 boolean
				case 9 : System.out.println("\n===== 메인 메뉴로 돌아갑니다. =====\n"); break;
				case 0 : System.out.println("\n===== 프로그램 종료 =====\n");
						 // JVM 강제 종료 구문
						 // 매개변수는 기본 0, 다른 숫자는 오류를 의미
						 System.exit(0);
				default : System.out.println("\n*** 메뉴 번호만 입력 해주세요 ***\n");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("\n*** 입력 형식이 올바르지 않습니다. ***\n");
				sc.nextLine();
				input = -1;
			}
			
		} while (input != 9);
		// 9 입력 받으면 while 문 끝나고 MainView 단으로 돌아감
	}

	/**
	 * 내 정보 조회
	 */
	public void selectMyInfo() {
		System.out.println("\n===== 내 정보 조회 =====\n");
		
		// 회원 번호, 아이디, 이름, 성별(남/여), 가입일
		// Session.loginMember 이용
		
		System.out.println("회원 번호 : " + Session.loginMember.getMemberNo());
		System.out.println("아이디 : " + Session.loginMember.getMemberId());
		System.out.println("이름 : " + Session.loginMember.getMemberName());
		
		if(Session.loginMember.getMemberGender().equals("M")) {
			System.out.println("성별 : 남");
		} else {
			System.out.println("성별 : 여");
		}
		
		System.out.println("가입일 : " + Session.loginMember.getEnrollDate());
	}
	
	/**
	 * 회원 목록 조회
	 */
	public void selectMemberList() {
		System.out.println("\n===== 회원 목록 조회 =====\n");
		
		try {
			// 회원 목록 조회 서비스 호출 후 결과 반환받기
			List<Member> memberList = service.selectMemberList();
			
			if(memberList.isEmpty()) {
				System.out.println("\n===== 조회 결과가 없습니다. =====\n");
				return;
				// 해당 메서드 종료하고 돌아감
			}
			// 조회 결과가 있다면
			// 1 user04 유저사 남
			// 2 user03 유저삼 여
			// ...
			
			// MEMBER_NO 아니고 그냥 List 순서임
			for(int i = 0 ; i < memberList.size() ; i ++) {
				System.out.printf("%d\t%s\t\t%s\t\t%s\n",
						i + 1,
						memberList.get(i).getMemberId(),
						memberList.get(i).getMemberName(),
						memberList.get(i).getMemberGender());
			}
			
		} catch (Exception e) {
			System.out.println("\n***** 회원 목록 조회 중 예외 발생 *****\n");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 내 정보 수정
	 */
	public void updateMember() {
		System.out.println("\n===== 내 정보 수정 =====\n");
		
		System.out.print("수정할 이름 : ");
		String memberName = sc.next();
		
		String memberGender = null;
		while(true) {
			System.out.print("수정할 성별(M/F) : ");
			memberGender = sc.next().toUpperCase();
			
			if(memberGender.equals("M") || memberGender.equals("F")) {
				break;
			}
			System.out.println("*** M 또는 F를 입력해주세요 ***");
		}
		
		try {
			// 회원 정보 수정 서비스 호출 및 결과 반환받기
			int result = service.updateMember(memberName, memberGender, Session.loginMember.getMemberNo());
			// Session.loginMember.getMemberNo() : 현재 로그인한 회원의 번호
			
			if(result > 0) {
				System.out.println("\n=== 수정 되었습니다 ===\n");
				
				// Service 를 호출해서 DB만 수정
				// -> DB와 Java 프로그램 데이터 동기화
				Session.loginMember.setMemberName(memberName);
				Session.loginMember.setMemberGender(memberGender);
				
			} else {
				System.out.println("\n*** 수정 실패 ***\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

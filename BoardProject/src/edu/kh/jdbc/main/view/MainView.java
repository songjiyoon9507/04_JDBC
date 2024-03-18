package edu.kh.jdbc.main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.common.Session;

public class MainView {

	private Scanner sc = new Scanner(System.in);
	
	/**
	 * 메인 메뉴 출력 View
	 */
	public void mainMenu() {
		
		int input = 0; // 메뉴 선택용 변수
		
		do {
			
			try {
				
				// main 메뉴 출력할 때 로그인 O / X 따져줄 거

				if(Session.loginMember == null) { // 로그인 X
					
					System.out.println("\n===== 회원제 게시판 프로그램 =====\n");
					System.out.println("1. 로그인");
					System.out.println("2. 회원가입");
					System.out.println("0. 프로그램 종료");
					
					System.out.print("\n메뉴 선택 : ");
					input = sc.nextInt();
					sc.nextLine(); // 입력 버퍼 개행 문자 제거
					
					switch (input) {
//					case 1 : login(); break;
//					case 2 : signUp(); break;
					case 0 : System.out.println("\n=== 프로그램 종료 ===\n"); break;
					default : System.out.println("\n=== 메뉴 번호만 입력해주세요. ===\n");
					}
					
				} else { // 로그인 O
					
					System.out.println("\n===== 로그인 메뉴 =====\n");
					System.out.println("1. 회원 기능");
					System.out.println("2. 게시판 기능");
					System.out.println("3. 로그아웃");
					System.out.println("0. 프로그램 종료");
					
					System.out.print("\n메뉴 선택 : ");
					input = sc.nextInt();
					sc.nextLine(); // 입력 버퍼 개행 문자 제거
					
					switch(input) {
//					case 1 : 회원기능view ; break;
//					case 2 : 게시판기능view ; break;
					case 3 : System.out.println("\n=== 로그아웃 되었습니다. ===\n");
							 Session.loginMember = null;
							 // 참조 하고 있던 로그인 회원 객체 없앰
							 break;
					case 0 : System.out.println("\n=== 프로그램 종료 ===\n"); break;
					default : System.out.println("\n=== 메뉴 번호만 입력해주세요. ===\n");
					}
				}
				
			} catch (InputMismatchException e) {
				System.out.println("\n*** 입력 형식이 올바르지 않습니다.***\n");
				sc.nextLine(); // 입력 버퍼에 잘못된 문자열 제거
				// input 0인 상태로 while문 종료 방지
				input = -1;
//				e.printStackTrace();
			}
			
		} while (input != 0);
	}
}

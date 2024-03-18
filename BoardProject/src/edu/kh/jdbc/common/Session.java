package edu.kh.jdbc.common;

import edu.kh.jdbc.member.model.dto.Member;

public class Session {

	// 웹페이지 등등
	// client 와 server 간 지속적인 상태 유지를 가능하게 해주는 개념
	// 로그인 한번 하면 어디를 왔다갔다 하든 로그인한 기록을 기억하고 있음
	// session 이라는 공간 안에 로그인한 정보를 기억하고 있다가
	// 웹 상에서 필요할 때 로그인 한 사람의 정보를 갖다 씀
	// 메인 페이지 -> 마이페이지 -> 내 정보 수정
	// 현재 로그인 된 사람 정보를 session 에서 갖다 씀
	// 글 쓸 때도
	// 웹 페이지가 client
	// -> server 배울 때 사용
	
	// 로그인 : DB에 기록된 회원 정보를 가지고 오는 것.
	//			-> 한번 로그인 되면 사용자가 로그아웃을 할 때 까지
	//             프로그램에서 회원 정보가 유지되어야 함
	
	// 필드
	public static Member loginMember = null;
	
	// loginMember == null -> 로그아웃 상태
	// loginMember != null -> 로그인 상태
	
	
}

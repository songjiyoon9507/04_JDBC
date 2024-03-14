package edu.kh.home.jdbc1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCExample1 {
	
	public static void main(String[] args) {
		
		// JDBC(Java DataBase Connectivity) : Java 에서 DB에 연결할 수 있게 해주는
		//                                    Java Programming API
		//                                    --> API에 I가 interface
		// (Java 에 기본 내장된 인터페이스)
		// JDBC는 java.sql 패키지에서 제공함
		
		// JDBC를 이용해서 어플리케이션을 만듦
		
		// * JDBC를 이용한 어플리케이션 만들 때 필요한 것 *
		// 하나의 프로젝트를 만들 때 필요한 것
		// 1. Java 의 JDBC 관련 인터페이스 필요 (기본 내장)
		// 2. DBMS 필요 (Oracle) (Java 와 DB 연결)
		// 3. Oracle 에서 Java 와 연결할 때 사용할
		//    JDBC를 상속받아 구현할 클래스 모음 (ojdbc10.jar 라이브러리)
		/*
		 * --> JDBC Interface 와 Oracle DBMS 연결할 때 Oracle JDBC Driver 필요 이게 ojdbc10.jar
		 * 라이브러리에 있음
		 * 라이브러리는 기능의 집합
		 * 내가 필요할 때만 추가해서 갖다 쓰면 됨
		 * 그래서 프로젝트 단위에 추가함
		 * 해당 프로젝트에서 필요할 때 추가
		 * Java Project 우클릭 -> Properties -> Java Build Path ->
		 * Libraries -> Classpath -> Add External JARs...
		 * -> ojdbc10.jar 파일 찾아가서 파일 열기
		 * (유형이 jar 파일로 되어있어야함)
		 */
		
		// 1단계 : JDBC 객체 참조 변수 선언 (java.sql 패키지의 인터페이스)
		
		Connection conn = null;
		// java.sql 패키지 인터페이스 import
		// DB 연결 정보를 담을 객체
		// -> DBMS 타입, 이름, IP, Port, 계정명, 비밀번호 저장
		// -> DBeaver 의 계정 접속 방법과 유사함
		// * Java 와 DB 사이를 연결해주는 통로
		
		Statement stmt = null;
		// java.sql 패키지에 있는 거 import 해줘야함
		// beans 에 있는 거 import 하면 JDBC 통신 안됨
		// Connection 을 통해서 SQL 문을 DB에 전달하여 실행하고
		// 생성된 결과 (ResultSet, 성공한 행의 개수)를 반환(Java)하는데 사용되는 객체
		
		ResultSet rs = null;
		// SELECT 질의 성공 시 반환되는데
		// 조회 결과 집합을 나타내는 객체
		
		// java 와 DB 연결하는 거라서 예외가 많이 발생함
		try {
			// 2단계 : 참조변수에 알맞은 객체 대입
			
			// 1. DB 연결에 필요한 Oracle JDBC Driver 메모리에 로드하기
			// 런타임 시점에 해당 경로의 클래스를 동적으로 로드함
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Oracle JDBC Driver 경로 ojdbc 상에서 정해준 문자열 (경로가 지정되어있음)
			// Run 타임 시점에 oracle 드라이버 경로가 메모리에 동적으로 올라감
			// -> () 안에 작성된 클래스의 객체를 반환
			// -> oracle.jdbc.driver 경로
			// -> OracleDriver 클래스명
			// -> 메모리에 객체가 생성되어지고 JDBC가 필요할 때 알아서 참조하여 사용
			// --> 생략해도 자동으로 메모리 로드가 진행됨 ojdbc 드라이버를 올려놔서
			// ---> ojdbc10.jar
			// --> (명시적으로 작성하는 걸 권장함)
			
			// 메모리에 오라클 드라이버 객체가 올라가져있는 형태가 됨
			// properties 에서 Classpath 올려둔 것이 메모리에 오라클 드라이버 객체 올려둔 것
			// 하지만 Class.forName 으로 명시적으로 작성
			
			/*
			 * JDBC 와 DB 연결할 때 첫번째로 해야할 일 드라이버 올리는 거
			 * Classpath 추가 + Class.forName(ojdbc 드라이버 경로)
			 * 메모리에 드라이버 객체 올리는 일
			 */
			
			// 2. 연결 정보를 담은 Connection 을 생성
			
		} catch(ClassNotFoundException e) {
			// oracle JDBC 경로 설정할 때 잘못 설정되면 나오는 올휴
			System.out.println("JDBC 드라이버 경로가 잘못 작성됨");
		}
		
	}
}

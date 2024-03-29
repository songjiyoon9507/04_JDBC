package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {

	public static void main(String[] args) {
		
		// JDBC(Java DataBase Connectivity) : Java 에서 DB에 연결할 수 있게 해주는 Java Programming API (Java 에 기본 내장된 인터페이스)
		// java.sql 패키지에서 제공
		
		// * JDBC를 이용한 어플리케이션 만들 때 필요한 것 *
		// 1. Java 의 JDBC 관련 인터페이스
		// 2. DBMS (Oracle)
		//    --> Oracle DBMS 연결해서 사용하려면 Oracle JDBC Driver 필요 (ojdbc)
		// 3. Oracle 에서 Java 와 연결할 때 사용할
		//    JDBC를 상속받아 구현할 클래스 모음 (ojdbc10.jar 라이브러리)
		// project 우클릭 Properties 클릭 Java Build Path 클릭
		// Libraries 클릭 ClassPath 클릭 add external jars 에서 ojdbc10.jar 추가
		
		// 1단계 : JDBC 객체 참조 변수 선언 (java.sql 패키지의 인터페이스)
		
		Connection conn = null;
		// Connection 도로
		// DB 연결 정보를 담을 객체
		// -> DBMS 타입, 이름, IP, Port, 계정명, 비밀번호 저장
		// -> DBeaver의 계정 접속 방법과 유사함
		// * Java 와 DB 사이를 연결해주는 통로
		
		Statement stmt = null;
		// Statement 셔틀버스
		// sql 패키지에 있는 거 import 해야함
		// Connection 을 통해서
		// SQL 문을 DB에 전달하여 실행하고
		// 생성된 결과 (ResultSet, 성공한 행의 개수 (Number))를 반환(Java)하는데 사용되는 객체
		
		ResultSet rs = null;
		// java.sql 패키지
		// SELECT 질의 성공 시 반환되는데
		// 조회 결과 집합을 나타내는 객체
		
		// java 와 DB 연결하는 거라서 예외 많이 발생함
		try {
			// 2단계 : 참조변수에 알맞은 객체 대입
			
			// 1. DB 연결에 필요한 Oracle JDBC Driver 메모리에 로드하기
			// 런타임 시점에 해당 경로의 클래스를 동적으로 로드함
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Class<?> java.lang.Class.forName(String className) throws ClassNotFoundException
			// OJDBC 경로는 이렇게 저장되어있음
			// -> () 안에 작성된 클래스의 객체를 반환
			// 앞에까진 경로 뒤에는 클래스 이름
			// -> 메모리에 객체가 생성되어지고 JDBC 필요 시 알아서 참조하여 사용
			// --> 생략해도 자동으로 메모리 로드가 진행됨 (명시적으로 작성하는 걸 권장)
			
			// DriverManager에서 getConnection메소드 이용하게 되면
			// 메모리에 있었던 드라이버 정보를 가지고 와서
			// ojdbc 드라이버를 확인하고
			// oracle db랑 java 통로 만들어줌
			
			// 2. 연결 정보를 담은 Connection 을 생성
			// DB와 Java 왔다갔다 하는 도로를 뚫겠다.
			// -> DriverManager 객체를 이용해서 Connection 객체를 만들어 얻어옴
			// 어떤 식으로 뚫어야하는지 정보가 필요한데
			// OracleDriver + 연결 정보
			
			String type = "jdbc:oracle:thin:@"; // JDBC 드라이버 종류
			// DBeaver 들어가서 확인할 수 있음 계정 연결하는 곳에서
			// URL
			String ip = "localhost"; // DB 서버 컴퓨터 IP 전달
			// == 127.0.0.1 (loop back IP) 본인 IP 주소로 돌아온다고 해서 loop back IP
			
			String port = ":1521"; // 포트번호
			// DB 기본 포트 1521
			
			String sid = ":XE"; // DB 이름
			
			// url == jdbc:oracle:thin:@localhost:1521:XE
			// 연결될 DB의 정보
			
			String user = "kh_sjy"; // 사용자 계정
			
			String pw = "kh1234"; // 비밀번호
			
			// DriverManager :
			// 도로 뚫으려면 정보를 전달해줘야함
			// 메모리에 로드된 JDBC 드라이버를 이용해서
			// Connection 객체를 만드는 역할.
			conn = DriverManager.getConnection(type+ip+port+sid, user, pw);
			// 보내줘야 하는 값 URL, user, pw
			// Unhandled exception type SQLException
			// -> 예외 catch 해줘야함
			
			// 중간 확인
//			System.out.println(conn);
			// oracle.jdbc.driver.T4CConnection@72d1ad2e
		
			// 3. SQL 작성
			// ** JAVA 에서 작성되는 SQL은 마지막에 ; 을 찍으면 안됨 **
			// 안 굴러감
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE";
			
			// 4. Statement 객체 생성
			// -> Connection 객체를 통해서 생성
			// 셔틀 버스 만든 거
			// 변수에 담아줘야함
			stmt = conn.createStatement();
			
			// 5. 생성된 Statement 객체에
			// SQL 을 적재하여 실행한 후 executeQuery
			// 결과를 반환받아와서
			// rs 변수에 저장
			rs = stmt.executeQuery(sql);
			// DB에 보낸 후 ResultSet까지 반환
			// executeQuery() : SELECT문 수행 메서드, ResultSet 반환
			
			// 3단계 : SQL을 수행해서 반환 받은 결과(ResultSet)를
			//         한 행씩 접근해서 컬럼값 얻어오기
			
			while(rs.next()) {
				// rs.next() : rs가 참조하고 있는 ResultSet 객체의
				//             첫 번째 컬럼부터 순서대로 한 행씩 이동하며
				//             다음 행이 있을 경우 true, 없으면 false 반환
				
				// rs.get자료형("컬럼명")
				// 자바 자료형으로 표현해야함
				String empId = rs.getString("EMP_ID"); // "200"
				// 현재 행의 "EMP_ID" 문자열 컬럼의 값을 얻어옴
				
				String empName = rs.getString("EMP_NAME"); // "선동일"
				
				int salary = rs.getInt("SALARY"); // 8,000,000
				
				Date hireDate = rs.getDate("HIRE_DATE"); // YYYY-MM-DD 1990-02-06
				// 자바에서는 이 형식으로 오버라이딩 돼있음
				// 시,분,초 안 넘어옴
				// java.sql 에 있는 Date 타입
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %s\n",
								   empId, empName, salary, hireDate);
				// hireDate 뒤에는 toString 붙여도 되고 안 붙여도 됨
				// java.sql.Date의 toString() 은 yyyy-mm-dd 형식으로 오버라이딩 되어있음.
				// 시, 분, 초 출력하려면 timestamp 사용해야함
			}
			
		} catch(ClassNotFoundException e) {
			// JDBC 경로 잘못 설정 됐을 때 나오는 에러
			System.out.println("JDBC 드라이버 경로가 잘못 작성됨");
		} catch(SQLException e) {
			// SQLException : DB 관련 최상위 예외
			e.printStackTrace();
		} finally {
			
			// 4단계 : 사용한 JDBC 객체 자원 반환
			// Connection, Statement, ResultSet 생성 역순으로 닫는 것을 권장
			try {
				// 결과 닫고 셔틀버스 닫고 도로 닫고
				// 열 때는 반대로
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}

package edu.kh.home.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			// throws ClassNotFoundException
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
			// -> DriverManager 객체를 이용해서 Connection 객체를 만들어 얻어옴
			
			// DriverManager가 드라이버정보 + 연결정보를 보고 java 와 DB 사이에
			// 통로를 만들어줌 Oracle 에 컴퓨터 IP와 포트번호 아이디 비밀번호를 가지고
			// java 와 DB 연결
			
			// 연결될 DB의 정보
			String type = "jdbc:oracle:thin:@"; // JDBC 드라이버 종류
			String ip = "localhost"; // DB 서버 컴퓨터 IP 지금 현재 DP 서버 컴퓨터가 내 컴퓨터임
			// 혹은 127.0.0.1 (loop back ip) 본인 IP 주소로 돌아오는 거
			String port = ":1521"; // DB 포트번호 기본값이 1521
			String sid = ":XE"; // DB 종류 이름
			
			// url = jdbc:oracle:thin:@localhost:1521:XE
			
			// 연결될 User의 정보
			String user = "kh_sjy"; // 사용자 계정
			String pw = "kh1234"; // 비밀번호
			
			// DriverManager에게 전달해줘야함
			// DriverManager :
			// 메모리에 로드된 JDBC 드라이버를 이용해서
			// Connection 객체를 만드는 역할 (Connection 객체가 도로)
			// DriverManager 는 도로 만드는 책임자
			conn = DriverManager.getConnection(type+ip+port+sid, user, pw);
			// 도로 만들 떄 책임자에게 전해줘야하는 3가지
			// url, 계정명, 비밀번호
			// 빨간줄
			// Unhandled exception type SQLException
			// catch 구문에서 SQLException 잡아줌
			
			// DriverManager가 Connection 만들어서 반환해준 걸 conn 에 담아줌
			// conn 은 Connection 객체
			
			// 중간확인
			// 주소 뜨면 잘 연결된 거
			System.out.println(conn);
//			oracle.jdbc.driver.T4CConnection@72d1ad2e
			
			// 3. SQL 작성
			// 버스에 태울 승객 SQL
			// java 에서 작성되는 SQL 은 마지막에 ; 을 찍으면 안됨 (쌍따옴표 안에)
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE";
			
			// 4. Statement 객체 생성
			// -> Connection 객체를 통해서 생성
			stmt = conn.createStatement();
			// Statement 를 만들어서 반환해준 객체를 stmt 에 담아줌
			
			// 5. 생성된 Statement 객체에
			// SQL 을 적재하여 실행한 후
			// 결과를 반환받아와서
			// rs 변수에 저장
			rs = stmt.executeQuery(sql);
			// SELECT 문을 수행 DB로 보내고 반환 받는 것까지
			// executeQuery() : SELECT문 수행 메서드, ResultSet 반환
			// INSERT, UPDATE 할 때는 다른 메서드 사용함
			
			// 3단계 : SQL을 수행해서 반환 받은 결과(ResultSet)를
			//         한 행씩 접근해서 컬럼값 얻어오기
			// 다음 행이 없을 때 까지
			
			// 반복문 사용
			while(rs.next()) {
				// rs.next() : rs 가 참조하고 있는 ResultSet 객체의
				//             첫 번째 컬럼부터 순서대로 한 행씩 이동하며
				//             다음 행이 있을 경우 true, 없으면 false 반환
				
				// rs.get자료형("컬럼명")
				String empId = rs.getString("EMP_ID"); // 첫번쪠 turn 에 "200"
				// 현재 행의 "EMP_ID" 문자열 컬럼의 값을 얻어옴
				
				String empName = rs.getString("EMP_NAME"); // 첫번쪠 turn 에 "선동일"
				
				int salary = rs.getInt("SALARY"); // 첫번째 turn 에 8,000,000
			
				// java.sql 에 있는 Date 타입
				Date hireDate = rs.getDate("HIRE_DATE"); // YYYY-MM-DD 1990-02-06
				// DB는 시분초까지 저장
				// java 는 년월일만 넘어옴 오버라이딩 되어있음
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %s\n",
								   empId, empName, salary, hireDate);
				// java.sql.Date 의 toString() 은 yyyy-mm-dd 형식으로 오버라이딩 되어있음.
				// 시, 분, 초 출력하려면 timeStamp 사용해야함
			}
			
		} catch(ClassNotFoundException e) {
			// oracle JDBC 경로 설정할 때 잘못 설정되면 나오는 올휴
			System.out.println("JDBC 드라이버 경로가 잘못 작성됨");
		} catch(SQLException e) {
			// SQLException : DB 관련 최상위 예외
			// DB 랑 연결할 때 많이 발생함
			e.printStackTrace();
		} finally {
			// 4단계 : 사용한 JDBC 객체 자원 반환 ( close() )
			// Connection, Statement, ResultSet 생성 역순으로 닫는 것을 권장
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}

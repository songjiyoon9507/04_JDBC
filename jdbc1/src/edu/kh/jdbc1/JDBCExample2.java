package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {
	
	public static void main(String[] args) {
		
		// 1단계 : JDBC 객체 참조변수 생성
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			// 2단계 : 참조변수에 알맞은 객체 대입
			// 오라클 드라이버 메모리에 로드하기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// DB 연결 정보
			String type = "jdbc:oracle:thin:@"; // JDBC 드라이버 종류
			String ip = "localhost"; // DB 서버 컴퓨터 IP 전달
			String port = ":1521"; // 포트번호
			String sid = ":XE"; // DB 이름
			String user = "kh_sjy"; // 사용자 계정
			String pw = "kh1234"; // 비밀번호
			
			// 드라이버 매니저에 연결해서 연결 정보 전달
			conn = DriverManager.getConnection(type+ip+port+sid, user, pw);
			// DriverManager가 Connection 을 만들어줌
			// Connection 객체 생성됨
			
			System.out.println("<입력 받은 급여보다 많이 받는(초과) 직원만 조회>");
			System.out.print("급여 입력 : ");
			int input = sc.nextInt(); // 2,000,000
			
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > " + input;
			// -> SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > 2000000
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			// 3단계 : SQL을 수행해서 반환받은 결과 (ResultSet)를
			//         한 행씩 접근해서 컬럼값 얻어오기
			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d\n",
									empId, empName, salary);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 4단계 : 사용한 JDBC 객체 자원 반환
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}

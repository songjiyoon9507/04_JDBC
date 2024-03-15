package edu.kh.home.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {
	public static void main(String[] args) {
		// 프로젝트에 드라이버 올리기
		// Class.forName으로 드라이버 경로 알려주기
		
		// 1단계 : JDBC 객체 참조변수 생성
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			// 2단계 : 참조변수에 알맞은 객체 대입하기
			// 드라이버 경로 명시적으로 표현
			// 오라클 드라이버 메모리에 로드하기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "kh_sjy", "kh1234");
			//                                JDBC 드라이버 종류, DB 서버 IP, 포트번호, DB 이름, 사용자계정, 비밀번호
			// DriverManager 가 Connection 을 만들어줌
			// Connection 객체 생성
			
			System.out.println("<입력 받은 급여보다 많이 받는(초과) 직원만 조회>");
			System.out.print("급여 입력 : ");
			int input = sc.nextInt(); // 2000000
			
			// EMP_ID, 이름, 급여
			// sql 작성
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > " + input;
			
			stmt = conn.createStatement();
			// Connection 통해서 Statement 객체 생성
			rs = stmt.executeQuery(sql);
			// Statement 가 SQL문을 들고 가서 query 수행 후 결과값 들고 돌아옴 (ResultSet)
			
			// 3단계 : SQL을 수행해서 반환받은 결과 (ResultSet)를
			//         한 행씩 접근해서 컬럼값 얻어오기
			while(rs.next()) {
				// 얻어오는 거 반복
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("사번 : %s | 이름 : %s | 급여 : %d\n", empId, empName, salary);
				
				/* << 출력 결과 >>
				<입력 받은 급여보다 많이 받는(초과) 직원만 조회>
				급여 입력 : 2000000
				사번 : 200 | 이름 : 선동일 | 급여 : 8000000
				사번 : 201 | 이름 : 송종기 | 급여 : 6000000
				사번 : 202 | 이름 : 노옹철 | 급여 : 3700000
				사번 : 203 | 이름 : 송은희 | 급여 : 2800000
				사번 : 204 | 이름 : 유재식 | 급여 : 3400000
				사번 : 205 | 이름 : 정중하 | 급여 : 3900000
				사번 : 207 | 이름 : 하이유 | 급여 : 2200000
				사번 : 208 | 이름 : 김해술 | 급여 : 2500000
				사번 : 209 | 이름 : 심봉선 | 급여 : 3500000
				사번 : 212 | 이름 : 장쯔위 | 급여 : 2550000
				사번 : 213 | 이름 : 하동운 | 급여 : 2320000
				사번 : 215 | 이름 : 대북혼 | 급여 : 3760000
				사번 : 216 | 이름 : 차태연 | 급여 : 2780000
				사번 : 217 | 이름 : 전지연 | 급여 : 3660000
				사번 : 218 | 이름 : 이오리 | 급여 : 2890000
				사번 : 220 | 이름 : 이중석 | 급여 : 2490000
				사번 : 221 | 이름 : 유하진 | 급여 : 2480000
				사번 : 222 | 이름 : 이태림 | 급여 : 2436240
				*/
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			// 4단계 : 사용한 JDBC 객체 자원 반환
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

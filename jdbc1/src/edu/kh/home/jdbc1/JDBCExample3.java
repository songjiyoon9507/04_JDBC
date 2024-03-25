package edu.kh.home.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.home.jdbc1.model.vo.Emp;

public class JDBCExample3 {

	public static void main(String[] args) {
		
		// 부서명을 입력 받아 같은 부서에 있는 사원의
		// 사원명, 부서명, 급여 조회
		
		// JDBC 객체 참조 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			
			System.out.print("부서명 입력 : "); // 총무부
			String input = sc.nextLine();
			
			// JDBC 참조변수에 알맞은 객체 대입

			// ojdbc 드라이버는 올라가져 있으니까
			// 명시적 표현
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh_sjy";
			String pw = "kh1234";
			conn = DriverManager.getConnection(url,user,pw);
			
			// xml 폴더는 CreateXMLFile 클래스 만들어서 xml 만드는 기능만 만들어둠
			
			// Class 부터 conn 까지 driver.xml에 만들어서 JDBCTemplate에서 connection 만듦
			// static 메서드 써서 필요할 때 가져다 씀
			
//			String sql = "SELECT EMP_NAME, NVL(DEPT_TITLE, '부서없음') DEPT_TITLE, SALARY"
//					+ " FROM EMPLOYEE"
//					+ " LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)"
//					+ " WHERE NVL(DEPT_TITLE, '부서없음') = '" + input + "'";
			// Java 에서 작성되는 SQL에 문자열 변수 추가할 경우
			// *** '' (DB 문자열 리터럴) 이 누락되지 않도록 주의 ***
			// 만약 '' 미작성 시 String 값은 컬럼명으로 인식되어
			// 부적합한 식별자 오류가 발생함
			
			// SQL 문장은 query.xml에 작성
			
//			stmt = conn.createStatement();
//			
//			rs = stmt.executeQuery(sql);
			
//			String sql = "SELECT EMP_NAME, NVL(DEPT_TITLE, '부서없음') DEPT_TITLE, SALARY"
//					+ " FROM EMPLOYEE"
//					+ " LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)"
//					+ " WHERE NVL(DEPT_TITLE, '부서없음') = ?";
			
			String sql = "SELECT * "
					+ " FROM EMPLOYEE"
					+ " LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)"
					+ " WHERE NVL(DEPT_TITLE, '부서없음') = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input);
			
			rs = pstmt.executeQuery();
			
			List<Emp> empList = new ArrayList<Emp>();
			
			while (rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				int salary = rs.getInt("SALARY");
				
				// Emp 객체를 생성하여 컬럼값 담기
//				Emp emp = new Emp(empName, deptTitle, salary);
				// 생성된 Emp 객체를 List에 추가
//				empList.add(emp);
				
				// List 에 바로 넣어주기
				empList.add(new Emp(empName, deptTitle, salary));
				// 조회된 행의 개수만큼 들어감
			}
			
			// 만약에 List 에 추가된 Emp 객체가 없다면 "조회 결과 없음"
			// 있다면 순차적으로 출력
			if(empList.isEmpty()) { // List 가 비어있을 경우
				System.out.println("조회 결과 없음");
			} else {
				// 향상된 for 문
				for(Emp list : empList) {
					System.out.println(list);
					// toString 오버라이딩된 형태로 출력됨
				}
			}
			
			/* << 출력 결과 >>
			부서명 입력 : 총무부
			이름 : 선동일 | 부서명 : 총무부 | 급여 : 8000000
			이름 : 송종기 | 부서명 : 총무부 | 급여 : 6000000
			이름 : 노옹철 | 부서명 : 총무부 | 급여 : 3700000
			*/
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
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

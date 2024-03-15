package edu.kh.home.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
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
			
			String sql = "SELECT EMP_NAME, NVL(DEPT_TITLE, '부서없음') DEPT_TITLE, SALARY"
					+ " FROM EMPLOYEE"
					+ " LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)"
					+ " WHERE NVL(DEPT_TITLE, '부서없음') = '" + input + "'";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			List<Emp> empList = new ArrayList<Emp>();
			
			while (rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				int salary = rs.getInt("SALARY");
				
//				Emp emp = new Emp(empName, deptTitle, salary);
				
				// List 에 바로 넣어주기
				empList.add(new Emp(empName, deptTitle, salary));
				
			}
			
			if(empList.isEmpty()) {
				System.out.println("조회 결과 없음");
			} else {
				for(Emp list : empList) {
					System.out.println(list);
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

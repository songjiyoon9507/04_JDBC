package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample6 {
	
	public static void main(String[] args) {
		
		// 전지연 사원이 속해있는 부서원들을 조회하시오.
		// 사번, 사원명, 전화번호, 고용일, 부서명
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
					"kh_sjy",
					"kh1234");
			
			String sql = "SELECT EMP_ID, EMP_NAME, PHONE, TO_CHAR(HIRE_DATE, 'YYYY-MM-DD') 입사일, DEPT_TITLE "
					+ "FROM EMPLOYEE "
					+ "JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE) "
					+ "WHERE DEPT_CODE = (SELECT DEPT_CODE "
					+ "FROM EMPLOYEE "
					+ "WHERE EMP_NAME = '유재식')";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String phone = rs.getString("PHONE");
				String hireDate = rs.getString("입사일");
				String deptTitle = rs.getString("DEPT_TITLE");
			
				System.out.printf("사번 : %s / 이름 : %s / 번호 : %s / 입사일 : %s / 부서명 : %s\n",
						empId, empName, phone, hireDate, deptTitle);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}

package edu.kh.home.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.home.jdbc1.model.vo.Employee;

public class JDBCExample5 {

	public static void main(String[] args) {
		
		// 입사일을 입력("2000-01-01") 받아
		// 입력 받은 값 보다 먼저 입사한 사람의
		// 이름, 입사일, 성별(M, F) 조회
		
		// VO 객체 이용
		// Employee 클래스 사용
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs =null;
		
		List<Employee> list = new ArrayList<Employee>();
		
		try {
			System.out.print("입사일 입력(YYYY-MM-DD) : ");
			String input = sc.next();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "kh_sjy", "kh1234");
			
			stmt = conn.createStatement();
			
			String sql = "SELECT EMP_NAME,"
					+ " TO_CHAR(HIRE_DATE, 'YYYY\"년\" MM\"월\" DD\"일\"') 입사일,"
					+ " DECODE( SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F' ) 성별"
					+ " FROM EMPLOYEE"
					+ " WHERE TO_CHAR(HIRE_DATE, 'YYYY-MM-DD') < '" + input + "'";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String hireDate = rs.getString("입사일");
				char gender = rs.getString("성별").toUpperCase().charAt(0);
				
				Employee emp = new Employee();
				emp.setEmpName(empName);
				emp.setHireDate(hireDate);
				emp.setGender(gender);
				
				list.add(emp);
				
			}
			
			if(list.isEmpty()) {
				System.out.println("조회 결과 없음");
			} else {
				for(Employee emp : list) {
					System.out.printf("이름 : %s / 입사일 : %s / 성별 : %c\n",
							emp.getEmpName(), emp.getHireDate(), emp.getGender());
				}				
			}
			
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

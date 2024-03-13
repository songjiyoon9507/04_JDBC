package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee2;

public class JDBCExample4_1 {
	
	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			
			System.out.print("직급명 입력 : ");
			String inputJobName = sc.next();
			
			System.out.print("급여 입력 : ");
			int inputSalary = sc.nextInt();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh_sjy";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(url, user, pw);

			String sql = "SELECT EMP_NAME, JOB_NAME, SALARY, SALARY*12 ANNUAL_INCOME "
					+ "FROM EMPLOYEE "
					+ "JOIN JOB USING (JOB_CODE) "
					+ "WHERE JOB_NAME = '" + inputJobName + "'"
					+ "AND SALARY > " + inputSalary;
						
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			List<Employee2> list = new ArrayList<Employee2>();
			
			while(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				int annualIncome = rs.getInt("ANNUAL_INCOME");
				
				list.add(new Employee2(empName, jobName, salary, annualIncome));
			}
			
			if(list.isEmpty()) {
				System.out.println("조회 결과 없음");
			} else {
				for(Employee2 emp : list) {
					System.out.println(emp);
				}
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

package edu.kh.home.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.home.jdbc1.model.vo.Employee;

public class JDBCExample4 {
	public static void main(String[] args) {
		
		// 직급명, 급여를 입력받아 해당 직급에서
		// 입력 받은 급여보다 많이 받는 사원의
		// 이름, 직급명, 급여, 연봉을 조회하여 출력
		// 단, 조회 결과가 없으면 "조회 결과 없음" 출력
		
		// 조회 결과가 있으면 아래와 같이 출력
		// 직급명 입력 : 부사장
		// 급여 입력 : 3000000
		// 송종기 / 부사장 / 6000000 / 72000000
		// 노옹철 / 부사장 / 3700000 / 44400000
		// ...
		
		// Employee (empName, jobName, salary, annualIncome)
		
		// 참조변수 선언 먼저
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Employee> empList = new ArrayList<Employee>();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("직급명 입력 : ");
		String inputJobName = sc.next();
		
		System.out.print("급여 입력 : ");
		int inputSalary = sc.nextInt();
		
		try {
			// connection
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 드라이버 경로 지정
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","kh_sjy","kh1234");
			
			stmt = conn.createStatement();
			
			String sql = "SELECT EMP_NAME, JOB_NAME, SALARY, SALARY*12 ANNUAL_INCOME"
					+ " FROM EMPLOYEE"
					+ " JOIN JOB USING(JOB_CODE)"
					+ " WHERE JOB_NAME = '" + inputJobName + "'"
					+ " AND SALARY > " + inputSalary;
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				int annualIncome = rs.getInt("ANNUAL_INCOME");
				
//				Employee emp = new Employee(empName, jobName, salary, annualIncome);
//				empList.add(emp);
				
				empList.add(new Employee(empName, jobName, salary, annualIncome));
			}
			
			if(empList.isEmpty()) {
				System.out.println("조회 결과 없음");
			} else {
				for(Employee list : empList) {
					System.out.println(list);
				}
			}
			
		} catch(Exception e) {
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

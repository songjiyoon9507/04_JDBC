package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample7 {
	
	public static void main(String[] args) {
		
		// 학과명을 입력 받아 ex) 환경조경학과

		// 입력 받은 학과와 같은 계열 (환경조경학과와 같은 계열은 자연과학임)
		// 학과들의 학과 별 전공과목 평점을 구하세요

		// 계열 학과명, 전공평점 (평점은 소수점 한자리까지만 반올림)
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			System.out.print("학과명 입력 : ");
			String input = sc.next();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "workbook";
			String pw = "workbook";
			
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT DEPARTMENT_NAME, ROUND(AVG(POINT), 1) 평점 "
					+ "FROM TB_STUDENT "
					+ "JOIN TB_DEPARTMENT USING (DEPARTMENT_NO) "
					+ "JOIN TB_GRADE USING (STUDENT_NO) "
					+ "WHERE CATEGORY = (SELECT CATEGORY "
					+ "FROM TB_DEPARTMENT "
					+ "WHERE DEPARTMENT_NAME = '" + input +"')"
					+ "GROUP BY DEPARTMENT_NAME";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String name = rs.getString("DEPARTMENT_NAME");
				double avg = rs.getDouble("평점");
				
				System.out.printf("학과명 : %s / 평점 : %.1f\n", name, avg);
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

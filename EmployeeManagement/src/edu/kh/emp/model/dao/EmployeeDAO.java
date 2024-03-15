package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.vo.Employee;

public class EmployeeDAO {
	
	// JDBC 객체 참조변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	// query 가져와서 저장
	
	public EmployeeDAO() {
		
		try {
			prop = new Properties();
			
			prop.loadFromXML( new FileInputStream("query.xml"));
			// key value 형식으로 파일 로드해서 저장
			// key value 묶어서 prop 에 저장해둠
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/** 전체 사원 정보 조회 DAO
	 * @param conn
	 * @return List<Employee>
	 */
	public List<Employee> selectAll(Connection conn) throws Exception {
		
		// 결과 저장용 변수 선언
		List<Employee> list = new ArrayList<Employee>();
		
		// Exception view 까지 던질 거임
		// View 단에서 try catch 구문 작성해둠
		
		try {
			
			// SQL 가져오기
			String sql = prop.getProperty("selectAll");
			
			// service 에서 전달 받아온 Connection 객체
			// Statement 객체 생성 (버스 먼저 만들고 승객 보내야함)
			stmt = conn.createStatement();
			
			// SQL을 수행 후 결과(ResultSet) 반환 받음
			rs = stmt.executeQuery(sql);
			
			// 조회 결과를 얻어와 한 행씩 접근하여
			// Employee 객체를 생성 후 컬럼값 담기
			// -> List 담기
			while(rs.next()) { // rs.next() 다음 행이 있으면 true, 없으면 false
				int empId = rs.getInt("EMP_ID");
				// EMP_ID 컬럼은 문자열 컬럼이지만
				// 저장된 값들은 모두 숫자 형태
				// -> DB에서 자동으로 형변환 진행해서 얻어옴
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId ,empName, empNo, email, phone,
											departmentTitle, jobName, salary);
				list.add(emp); // List 담기
			} // while 문 종료
			
		} finally {
			// 사용한 JDBC 객체 자원 반환
			close(rs);
			close(stmt);
			// JDBCTemplate 에 메서드 만들어둔 거 사용해서 닫기
		}
		return list;
	}

	/** 사원 정보 추가 DAO
	 * @param conn
	 * @param emp
	 * @return result (1/0)
	 */
	public int insertEmployee(Connection conn, Employee emp) throws Exception {

		int result = 0;
		
		try {
			
			// SQL 작성
			String sql = prop.getProperty("insertEmployee");
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// 생성될 때 SQL 을 싣고 생성함
			
			// ? (위치홀더)에 알맞은 값 대입
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setInt(11, emp.getManagerId());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/** 사번이 일치하는 사원 정보 조회 DAO
	 * @param conn
	 * @param input
	 * @return Employee emp
	 * @throws Exception
	 */
	public Employee selectEmpId(Connection conn, int input) throws Exception{

		Employee emp = null;
		
//		int result = 0;
		
		try {
			String sql = prop.getProperty("selectEmpId");
			
//			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, "
//					+ "NVL(DEPT_TITLE, '부서없음') DEPT_TITLE, JOB_NAME, SALARY "
//					+ "FROM EMPLOYEE "
//					+ "LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)\r\n"
//					+ "JOIN JOB USING(JOB_CODE) "
//					+ "WHERE EMP_ID = " + input;
			
//			stmt = conn.createStatement();
//			
//			rs = stmt.executeQuery(sql);

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			// stmt - ? (위치홀더 없을 때)
			// pstmt - ? (위치홀더 있을 때)
			// executeQuery - SELECT -> ResultSet
			// executeUpdate - DML(Update/Delete/Insert) -> int(성공한 행의 갯수)
			
			// executeQuery() 는 ResultSet이 돌아오고
			// executeUpdate() 는 int 값이 돌아옴
			// pstmt 든, stmt 든 둘 다 메서드 사용 가능함
			
//			while(rs.next()) {
//				
//				int empId = rs.getInt("EMP_ID");
//				String empName = rs.getString("EMP_NAME");
//				String empNo = rs.getString("EMP_NO");
//				String email = rs.getString("EMAIL");
//				String phone = rs.getString("PHONE");
//				String departmentTitle = rs.getString("DEPT_TITLE");
//				String jobName = rs.getString("JOB_NAME");
//				int salary = rs.getInt("SALARY");
//				
//				emp = new Employee(empId ,empName, empNo, email, phone,
//						departmentTitle, jobName, salary);
//				
//			}
			
			// 한줄만 출력되기 때문에 굳이 while 문 쓸 필요 없음
			if(rs.next()) {
				// 조회된 행이 1개라도 있으면
//				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
//				emp = new Employee(empId ,empName, empNo, email, phone,
//						departmentTitle, jobName, salary);
				emp = new Employee(input ,empName, empNo, email, phone,
						departmentTitle, jobName, salary);
				// 어차피 EMP_ID 같기 때문에 매개변수로 넘어온 input 넣어주면 됨
				// 굳이 얻어올 필요 없음
			}
			
			/* rs.next() 안하면 에러뜸
			
			java.sql.SQLException: ResultSet.next가 호출되지 않았음
			
			int empId = rs.getInt("EMP_ID");
			String empName = rs.getString("EMP_NAME");
			String empNo = rs.getString("EMP_NO");
			String email = rs.getString("EMAIL");
			String phone = rs.getString("PHONE");
			String departmentTitle = rs.getString("DEPT_TITLE");
			String jobName = rs.getString("JOB_NAME");
			int salary = rs.getInt("SALARY");
			
			emp = new Employee(empId ,empName, empNo, email, phone,
					departmentTitle, jobName, salary);
			*/
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return emp;
	}

//	public Employee updateEmployee(Connection conn, int empId, String email, String phone, int salary) throws Exception {
//		
//		Employee emp = null;
//		
//		try {
//			
//			String sql = prop.getProperty("updateEmployee");
//			
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setString(1, email);
//			pstmt.setString(2, phone);
//			pstmt.setInt(3, salary);
//			pstmt.setInt(4, empId);
//			
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				// 조회된 행이 1개라도 있으면
////				int empId = rs.getInt("EMP_ID");
//				String empName = rs.getString("EMP_NAME");
//				String empNo = rs.getString("EMP_NO");
//				String email = rs.getString("EMAIL");
//				String phone = rs.getString("PHONE");
//				String departmentTitle = rs.getString("DEPT_TITLE");
//				String jobName = rs.getString("JOB_NAME");
//				int salary = rs.getInt("SALARY");
//				
////				emp = new Employee(empId ,empName, empNo, email, phone,
////						departmentTitle, jobName, salary);
//				emp = new Employee(input ,empName, empNo, email, phone,
//						departmentTitle, jobName, salary);
//				// 어차피 EMP_ID 같기 때문에 매개변수로 넘어온 input 넣어주면 됨
//				// 굳이 얻어올 필요 없음
//			}
//			
//		} finally {
//			
//		}
//		// TODO Auto-generated method stub
//		return null;
//	}

	/** 사번이 일치하는 사원 정보 수정 DAO
	 * @param conn
	 * @param emp
	 * @return int result
	 */
	public int updateEmployee(Connection conn, Employee emp) throws Exception {

		int result = 0;
		
		try {
			String sql = prop.getProperty("updateEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteEmployee(Connection conn, int empId) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public List<Employee> selectDeptEmp(Connection conn, String deptTitle) throws Exception {
		
		List<Employee> list = new ArrayList<Employee>();

		try {
			String sql = prop.getProperty("selectDeptEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, deptTitle);
			
			// select 문 수행시 ResultSet 반환됨
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
//				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId ,empName, empNo, email, phone,
											deptTitle, jobName, salary);
				list.add(emp); // List 담기

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
	}

	public List<Employee> selectSalaryEmp(Connection conn, int salary) throws Exception {
		List<Employee> list = new ArrayList<Employee>();

		try {
			String sql = prop.getProperty("selectSalaryEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, salary);
			
			// select 문 수행시 ResultSet 반환됨
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int empSalary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId ,empName, empNo, email, phone,
						departmentTitle, jobName, empSalary);
				
				list.add(emp); // List 담기

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
	}

	public Map<String, Integer> selectDeptTotalSalary(Connection conn) throws Exception {

		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		
		try {
			String sql = prop.getProperty("selectDeptTotalSalary");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String deptCode = rs.getString("DEPT_CODE");
				int sumSalary = rs.getInt("TOTAL_SALARY");
				
				map.put(deptCode, sumSalary);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return map;
	}

	public Employee selectEmpNo(Connection conn, String empNo) throws Exception {
		
		Employee emp = null;

		try {
			
			String sql = prop.getProperty("selectEmpNo");
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, empNo);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");

				emp = new Employee(empId ,empName, empNo, email, phone,
								   departmentTitle, jobName, salary);

			}

		} finally {
			close(rs);
			close(stmt);
		}

		return emp;

	}

	public Map<String, Double> selectJobAvgSalary(Connection conn) throws Exception {

		Map<String, Double> map = new LinkedHashMap<String, Double>();
		
		try {
			String sql = prop.getProperty("selectJobAvgSalary");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String jobName = rs.getString("JOB_NAME");
				double avg = rs.getDouble("AVG");
				
				map.put(jobName, avg);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return map;
	}
}

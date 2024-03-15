package edu.kh.emp.model.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

public class EmployeeService {

	private EmployeeDAO dao = new EmployeeDAO();

	/** 전체 사원 정보 조회 서비스
	 * @return List<Employee>
	 */
	public List<Employee> selectAll() throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = getConnection();
		// JDBCTemplate import 할 때
		// 앞에 static 마지막에 .* 붙여서
		// JDBCTemplate. 안 쓰고 메서드 호출
		
		List<Employee> list = dao.selectAll(conn);
		// service 에서 생성한 connection 넘겨줘야함
		// DAO 에서 Statement 나 PreparedStatement 쓸 때 사용
		
		// 사용한 connection 반환만 해주면 됨
		// 트랜잭션 커밋 롤백은 필요없음 update insert delete 가 아니라
		close(conn);
		
		return list;
	}

	/** 사원 정보 추가 서비스
	 * @param emp
	 * @return result (1/0)
	 */
	public int insertEmployee(Employee emp) throws Exception{

		Connection conn = getConnection();
		
		int result = dao.insertEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	public Employee selectEmpId(int input) throws Exception {
		
		// Connection 생성
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpId(conn, input);
		
		close(conn);
		
		return emp;
	}

//	public Employee updateEmployee(int empId, String email, String phone, int salary) throws Exception {
//
//		Connection conn = getConnection();
//		
//		Employee updateEmp = dao.updateEmployee(conn, empId, email, phone, salary);
//		
//		close(conn);
//		
//		return updateEmp;
//	}

	/** 사번이 일치하는 사원 정보 수정 서비스
	 * @param emp
	 * @return int result (1/0)
	 */
	public int updateEmployee(Employee emp) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 사번이 일치하는 사원 정보 삭제 서비스
	 * @param empId
	 * @return result
	 * @throws Exception
	 */
	public int deleteEmployee(int empId) throws Exception {

		Connection conn = getConnection();
		
		int result = dao.deleteEmployee(conn, empId);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	public List<Employee> selectDeptEmp(String deptTitle) throws Exception{

		Connection conn = getConnection();
		
		List<Employee> list = dao.selectDeptEmp(conn, deptTitle);
		// 조회하는 거니까 commit rollBack 해줄 필요 없음
		
		close(conn);
		
		return list;
	}

	public List<Employee> selectSalaryEmp(int salary) throws Exception {

		Connection conn = getConnection();
		
		List<Employee> list = dao.selectSalaryEmp(conn, salary);
		
		close(conn);
		
		return list;
	}

	public Map<String, Integer> selectDeptTotalSalary() throws Exception {

		Connection conn = getConnection();
		
		Map<String, Integer> map = dao.selectDeptTotalSalary(conn);
		
		close(conn);
		
		return map;
	}

	public Employee selectEmpNo(String empNo) throws Exception {
		
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpNo(conn, empNo);
		
		close(conn);
		
		return emp;
	}

	public Map<String, Double> selectJobAvgSalary() throws Exception {

		Connection conn = getConnection();
		
		Map<String, Double> map = dao.selectJobAvgSalary(conn);
		
		close(conn);
		
		return map;
	}
	
}

package edu.kh.emp.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import edu.kh.emp.model.service.EmployeeService;
import edu.kh.emp.model.vo.Employee;

// 화면용 클래스
public class EmployeeView {

	private Scanner sc = new Scanner(System.in);
	
	// Service 객체 생성
	private EmployeeService service = new EmployeeService();
	
	// 메인 메뉴
	public void displayMenu() {
		
		int input = 0;
		
		do {
			
			try {
				// 메뉴 선택하면 Exception 발생해서 try catch 구문 작성
				System.out.println("\n========== [ 사원 관리 프로그램 ] ==========\n");
				System.out.println("1. 전체 사원 정보 조회");
				System.out.println("2. 새로운 사원 추가");
				System.out.println("3. 사번이 일치하는 사원 정보 조회");
				System.out.println("4. 사번이 일치하는 사원 정보 수정");
				System.out.println("5. 사번이 일치하는 사원 정보 삭제");
				
				// 추가
				System.out.println("6. 입력 받은 부서와 일치하는 모든 사원 정보 조회");
				// selectDeptEmp() : 부서명 입력 받기
				System.out.println("7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회");
				// selectSalaryEmp() : 급여 입력 받기
				System.out.println("8. 부서별 급여 합 전체 조회");
				// selectDeptTotalSalary() - HashMap<String, Integer> Key 부서 코드 value 급여
				// D1 : 8000000원
				// D2 : 5200000원
				// ... (이런 식으로 출력)
				System.out.println("9. 주민등록번호가 일치하는 사원 정보 조회");
				// selectEmpNo() : 주민등록번호 입력 받기
				System.out.println("10. 직급별 급여 평균 조회");
				// selectJobAvgSalary() : 소수점 첫째자리까지 조회 - HashMap<String, Double>
				// 대표 : 8000000.0원
				// 부사장 : 4850000.0원
				// ...
				// 사원 : 2017500.0원
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 >> ");
				input = sc.nextInt();
				
				switch(input) {
				case 1 : selectAll(); break;
				case 2 : insertEmployee(); break;
				case 3 : selectEmpId(); break;
				case 4 : updateEmployee(); break;
				case 5 : deleteEmployee(); break;
				case 6 : selectDeptEmp(); break;
				case 7 : selectSalaryEmp(); break;
				case 8 : selectDeptTotalSalary(); break;
				case 9 : selectEmpNo(); break;
				case 10 : selectJobAvgSalary(); break;
				case 0 : System.out.println("프로그램 종료을 종료합니다."); break;
				default : System.out.println("메뉴에 존재하는 번호만 입력하세요."); break;
				}
				
			} catch(InputMismatchException e) {
				// 번호 안 넣고 다른 거 넣었을 때 나오는 Exception
				System.out.println("정수만 입력해주세요.");
				input = -1; // 반복문 첫 번쨰 바퀴에서 잘못입력하면 종료되는 상황을 방지
				// 안 넣어주면 input = 0; 인 상태로 계속이라서
				sc.nextLine(); // 입력버퍼에 남아있는 잘못 입력된 문자열 제거해서 무한반복 방지.
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		} while(input != 0);
		
	}

	/**
	 * 전체 사원 정보 조회 View
	 */
	public void selectAll() throws Exception {
		System.out.println("\n<전체 사원 정보 조회>\n");
		
		List<Employee> empList = service.selectAll();
		
		printAll(empList);
		
	}
	
	// 보조 메서드

	/**
	 * 전달받은 사원 List 모두 출력
	 */
	public void printAll(List<Employee> empList) {
		
		if(empList.isEmpty()) {
			System.out.println("조회된 사원 정보가 없습니다.");
		} else {
			System.out.println("\n 사번 |   이름  | 주민 등록 번호 |      이메일     |   전화 번호  |    부서    | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			
			for(Employee emp : empList) {
				System.out.printf(" %2d  | %4s | %s | %15s | %12s | %s | %3s | %d\n",
					emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(),
					emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
			}
		}
		return;
	}

	/**
	 * 사원 정보 추가 View
	 */
	public void insertEmployee() throws Exception {
		System.out.println("\n<사원 정보 추가>\n");
		
		// 사번
		int empId = inputEmpId();
		
		System.out.print("이름 : ");
		String empName = sc.next();
		
		System.out.print("주민등록번호('-' 포함) : ");
		String empNo = sc.next();
		
		System.out.print("이메일 : ");
		String email = sc.next();
		
		System.out.print("전화번호 : ");
		String phone = sc.next();
		
		System.out.print("부서(D1~D9) : ");
		String deptCode = sc.next();
		
		System.out.print("직급(J1~J7) : ");
		String jobCode = sc.next();
		
		System.out.print("급여등급(S1~S6) : ");
		String salLevel = sc.next();
		
		System.out.print("급여 : ");
		int salary = sc.nextInt();
		
		System.out.print("보너스 : ");
		double bonus = sc.nextDouble();
		
		System.out.print("사수번호 : ");
		int managerId = sc.nextInt();
		
		Employee emp = new Employee(empId, empName, empNo, email, phone, salary, deptCode,
				jobCode, salLevel, bonus, managerId);
		
		int result = service.insertEmployee(emp);
		
		if(result > 0) {
			System.out.println("사원 정보 추가 성공");
		} else {
			System.out.println("사원 정보 추가 실패");
		}
	}
	
	// 보조 메서드
	/** 사번을 입력받아 반환하는 메서드
	 * @return empId
	 */
	public int inputEmpId() {
		System.out.print("사번 입력 : ");
		int empId = sc.nextInt();
		sc.nextLine(); // 입력 버퍼 개행 제거
		return empId;
	}
	
	// 보조메서드
	/**
	 * 사원 1명 정보 출력
	 * @param emp
	 */
	public void printOne(Employee emp) {
		if(emp == null) {
			System.out.println("\n조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("\n 사번 |   이름  | 주민 등록 번호 |        이메일        |  전화 번호  |  부서  | 직책 |  급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			
			System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
					emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(),
					emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
		}
	}

	/**
	 * 사번이 일치하는 사원 정보 조회
	 */
	public void selectEmpId() throws Exception {
		System.out.println("\n<사번이 일치하는 사원정보 조회>");
		// 사번 입력 받기(inputEmpId() 이용)
		int input = inputEmpId();
		
		// 서비스 호출 및 결과 반환받기
		Employee emp = service.selectEmpId(input);
		
		// printOne() 메서드 이용하여 결과 출력하기
		printOne(emp);
	}
	
	/**
	 * 사번이 일치하는 사원정보 수정(이메일, 전화번호, 급여)
	 */
	public void updateEmployee2() throws Exception {
		System.out.println("\n<사번이 일치하는 사원정보 수정>");
		int empId = inputEmpId();
		
		Employee emp = service.selectEmpId(empId);
		
		if(emp == null) {
			System.out.println("일치하는 회원 정보가 없습니다.");
		} else {
			System.out.print("수정할 이메일 입력 : ");
			String email = sc.next();
			
			System.out.print("수정할 전화번호 입력 : ");
			String phone = sc.next();
			
			System.out.print("수정할 급여 입력 : ");
			int salary = sc.nextInt();
			
//			Employee updateEmp = service.updateEmployee(empId, email, phone, salary);
			
		}
		
	}
	
	public void updateEmployee() throws Exception {
		System.out.println("\n<사번이 일치하는 사원정보 수정>\n");
		int empId = inputEmpId();
		
		System.out.print("이메일 : ");
		String email = sc.next();
			
		System.out.print("전화번호(- 제외) : ");
		String phone = sc.next();
			
		System.out.print("급여 : ");
		int salary = sc.nextInt();
			
		Employee emp = new Employee();
		
		emp.setEmpId(empId);
		emp.setEmail(email);
		emp.setPhone(phone);
		emp.setSalary(salary);
	
		// 성공한 행의 개수로 넘어옴
		int result = service.updateEmployee(emp);
		
		if(result > 0) {
			System.out.println("\n사원 정보가 수정되었습니다.");
		} else {
			System.out.println("\n사번이 일치하는 직원이 존재하지 않습니다.");
		}
	}
	
	/** 사번이 일치하는 사원 정보 삭제
	 * @throws Exception
	 */
	public void deleteEmployee() throws Exception {
		System.out.println("\n<사번이 일치하는 사원 정보 삭제>\n");
		
		int empId = inputEmpId();
		
		System.out.print("정말 삭제 하시겠습니까? (Y/N) : ");
		char input = sc.next().toUpperCase().charAt(0);
		// Y/N 대소문자 구분없이 입력 ok
		
		if(input == 'Y') {
			// 삭제 수행 서비스 호출
			// delete 행의 개수 돌아옴
			int result = service.deleteEmployee(empId);
			
			if(result > 0) {
				System.out.println("\n삭제되었습니다.");
			} else {
				System.out.println("\n사번이 일치하는 사원이 존재하지 않습니다.");
			}
			
		} else {
			System.out.println("\n취소되었습니다.");
		}
		
	}
	
	/** 입력 받은 부서와 일치하는 모든 사원 정보 조회
	 * @throws Exception
	 */
	public void selectDeptEmp() throws Exception {
		System.out.println("\n<입력 받은 부서와 일치하는 모든 사원 정보 조회>\n");
		
		System.out.print("부서명 입력 : ");
		String deptTitle = sc.next();
		
		// 같은 부서명 가진 사원들 select 여러 명
		List<Employee> empList = service.selectDeptEmp(deptTitle);
		
		printAll(empList);
		
//		if(empList.isEmpty()) {
//			System.out.println("\n부서명이 일치하는 사원이 존재하지 않습니다.");
//		} else {
//			System.out.println(" 사번 |   이름  | 주민 등록 번호 |      이메일     |   전화 번호  |    부서    | 직책 | 급여" );
//			System.out.println("------------------------------------------------------------------------------------------------");
//			
//			for(Employee emp : empList) {
//				System.out.printf(" %2d  | %4s | %s | %15s | %12s | %s | %3s | %d\n",
//					emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(),
//					emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
//			}
//		}
//		return;
	}
	
	public void selectSalaryEmp() throws Exception {
		System.out.println("\n<입력 받은 급여 이상을 받는 모든 사원 정보 조회>\n");
		
		System.out.print("급여 입력 : ");
		int salary = sc.nextInt();
		
		// select
		List<Employee> empList = service.selectSalaryEmp(salary);
		
		printAll(empList);
	}
	
	public void selectDeptTotalSalary() throws Exception {
		System.out.println("\n<부서별 급여 합 전체 조회>\n");
		
		Map<String, Integer> map = service.selectDeptTotalSalary();
		
		Set<Entry<String, Integer>> entrySet = map.entrySet();
		
		// 향상된 for 문 + EntrySet
		
		// Entry.getKey()   : Key 만 얻어오기
		// Entry.getValue() : Value 만 얻어오기
		for(Entry<String, Integer> entry : entrySet) {
			System.out.printf("%s : %d원\n",
							entry.getKey(),entry.getValue() );
		}
	}
	
	public void selectEmpNo() throws Exception {
		
		System.out.println("\n<주민등록번호가 일치하는 사원 정보 조회>\n");
		
		System.out.print("주민등록번호('-' 포함) : ");
		String empNo = sc.next();
		
		Employee emp = service.selectEmpNo(empNo);
		
		printOne(emp);
	}

	public void selectJobAvgSalary() throws Exception {
		System.out.println("\n<직급별 급여 평균 조회>\n");
		
		Map<String, Double> map = service.selectJobAvgSalary();
		
		Set<Entry<String, Double>> entrySet = map.entrySet();

		for(Entry<String, Double> entry : entrySet) {
			System.out.printf("%s : %.1f원\n",
							entry.getKey(),entry.getValue() );
		}
	}
}

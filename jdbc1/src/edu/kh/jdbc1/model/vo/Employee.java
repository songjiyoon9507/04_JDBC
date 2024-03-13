package edu.kh.jdbc1.model.vo;

public class Employee {
	private String empName; // 이름
	private String jobName; // 직급명
	private int salary; // 급여
	private long annualIncome; // 연봉
	
	// 필드 추가
	private String hireDate; // 입사일
	private char gender; // 남 M, 여 F

	public Employee() {}

	public Employee(String empName, String jobName, int salary, long annualIncome) {
		this.empName = empName;
		this.jobName = jobName;
		this.salary = salary;
		this.annualIncome = annualIncome;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public long getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(long annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getHireDate() {
		return hireDate;
	}
	
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	
	public char getGender() {
		return gender;
	}
	
	public void setGender(char gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return empName + " / " + jobName + " / " + salary + " / "
				+ annualIncome;
	}
	
}

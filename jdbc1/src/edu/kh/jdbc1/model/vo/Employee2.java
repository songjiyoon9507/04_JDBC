package edu.kh.jdbc1.model.vo;

public class Employee2 {

	private String empName;
	private String jobName;
	private int salary;
	private int annualIncome;
	
	public Employee2() {}

	public Employee2(String empName, String jobName, int salary, int annualIncome) {
		super();
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

	public int getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}

	@Override
	public String toString() {
		return empName + " / " + jobName + " / " + salary + " / " + annualIncome;
	}
	
}

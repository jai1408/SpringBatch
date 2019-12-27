package com.bsl.batch.job;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.bsl.batch.model.Employee;

@Component
public class JobProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee employeeDTO) throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeId(employeeDTO.getEmployeeId());
		employee.setFirstName(employeeDTO.getFirstName());
		employee.setLastName(employeeDTO.getLastName());
		employee.setEmail(employeeDTO.getEmail());
		employee.setAge(employeeDTO.getAge());
		System.out.println("inside processor " + employee.toString());
		return employee;
	}
}

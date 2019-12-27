package com.bsl.batch.job;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.bsl.batch.model.Employee;

@Component
public class JobProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee employee) throws Exception {
		return employee;
	}
}

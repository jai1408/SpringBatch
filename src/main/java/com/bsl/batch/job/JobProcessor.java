package com.bsl.batch.job;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.bsl.batch.model.Employee;

@Component
public class JobProcessor implements ItemProcessor<Employee, Employee> {

	private static final Logger logger = LoggerFactory.getLogger(JobProcessor.class);

	@Override
	public Employee process(Employee employee) throws Exception {
		logger.info("Inside JobProcessor. Employee = {}", employee.toString());
		employee.setEmployeeId(employee.getEmployeeId() + new Random().nextInt(10000000));
		return employee;
	}
}

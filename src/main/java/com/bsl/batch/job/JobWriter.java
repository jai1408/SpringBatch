package com.bsl.batch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bsl.batch.model.Employee;
import com.bsl.batch.repository.EmployeeRepo;

@Component
public class JobWriter implements ItemWriter<Employee> {
	
	private static final Logger logger = LoggerFactory.getLogger(JobWriter.class);

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		employeeRepo.saveAll(employees);
		//logger.info("{} employees saved in database", employees.size());
	}
}

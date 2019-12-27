package com.bsl.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableAsync;

import com.bsl.batch.job.JobProcessor;
import com.bsl.batch.job.JobWriter;
import com.bsl.batch.model.Employee;

@Configuration
@EnableAsync
public class BatchJobConfig {

	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private JobProcessor jobProcessor;
	private JobWriter jobWriter;
	public JobRepository jobRepository;

	@Bean
	public JobLauncher simpleJobLauncher() {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		return jobLauncher;
	}

	@Autowired
	public BatchJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			JobProcessor jobProcessor, JobWriter jobWriter, JobRepository jobRepository) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.jobProcessor = jobProcessor;
		this.jobWriter = jobWriter;
		this.jobRepository = jobRepository;
	}

	@Qualifier(value = "demo")
	@Bean
	public Job demoJob() throws Exception {
		return this.jobBuilderFactory.get("demo").start(demoStep()).build();
	}

	@Bean
	public Step demoStep() throws Exception {
		return this.stepBuilderFactory.get("step1").<Employee, Employee>chunk(5).reader(employeeReader())
				.processor(jobProcessor).writer(jobWriter).build();
	}

	@Bean
	@StepScope
	Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) throws Exception {
		return new ClassPathResource(fileName);
	}

	@Bean
	@StepScope
	public FlatFileItemReader<Employee> employeeReader() throws Exception {
		FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
		reader.setResource(inputFileResource(null));
		reader.setLineMapper(new DefaultLineMapper<Employee>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("employeeId", "firstName", "lastName", "email", "age");
						setDelimiter(DELIMITER_TAB);
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
					{
						setTargetType(Employee.class);
					}
				});
			}
		});
		return reader;
	}
}
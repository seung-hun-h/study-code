package com.example.springbatchguide.reader;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
public class JdbcPagingItemReaderJobConfiguration {
	private static final Logger log = LoggerFactory.getLogger(JdbcPagingItemReaderJobConfiguration.class);

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource; // DataSource DI

	public JdbcPagingItemReaderJobConfiguration(
		JobBuilderFactory jobBuilderFactory,
		StepBuilderFactory stepBuilderFactory,
		DataSource dataSource
	) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.dataSource = dataSource;
	}

	private static final int chunkSize = 10;

	@Bean
	public Job jdbcPagingItemReaderJob() throws Exception {
		return jobBuilderFactory.get("jdbcPagingItemReaderJob")
			.start(jdbcPagingItemReaderStep())
			.build();
	}

	@Bean
	public Step jdbcPagingItemReaderStep() throws Exception {
		return stepBuilderFactory.get("jdbcPagingItemReaderStep")
			.<Pay, Pay>chunk(chunkSize)
			.reader(jdbcPagingItemReader())
			.writer(jdbcPagingItemWriter())
			.build();
	}

	@Bean
	public JdbcPagingItemReader<Pay> jdbcPagingItemReader() throws Exception {
		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("amount", 2000);

		return new JdbcPagingItemReaderBuilder<Pay>()
			.pageSize(chunkSize)
			.fetchSize(chunkSize)
			.dataSource(dataSource)
			.rowMapper(new BeanPropertyRowMapper<>(Pay.class))
			.queryProvider(createQueryProvider())
			.parameterValues(parameterValues)
			.name("jdbcPagingItemReader")
			.build();
	}

	private ItemWriter<Pay> jdbcPagingItemWriter() {
		return list -> {
			for (Pay pay: list) {
				log.info("Current Pay={}", pay);
			}
		};
	}

	@Bean
	public PagingQueryProvider createQueryProvider() throws Exception {
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource); // Database에 맞는 PagingQueryProvider를 선택하기 위해
		queryProvider.setSelectClause("id, amount, tx_name, tx_date_time");
		queryProvider.setFromClause("from pay");
		queryProvider.setWhereClause("where amount >= :amount");

		Map<String, Order> sortKeys = new HashMap<>(1);
		sortKeys.put("id", Order.ASCENDING);

		queryProvider.setSortKeys(sortKeys);

		return queryProvider.getObject();
	}
}

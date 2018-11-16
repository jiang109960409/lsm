package com.lsm.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@MapperScan(basePackages = { "com.lsm.mapper" }, sqlSessionFactoryRef = "baseSqlSessionFactory")
@EnableTransactionManagement
@EnableCaching
@EnableAsync
@EnableScheduling
@Configuration
public class AppConfiguration {

	@Bean
	public StandardServletMultipartResolver getResolver() {
		return new StandardServletMultipartResolver();
	}

	@Primary
	@Bean("dataSourceProperty")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSourceProperties getDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean("baseDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource getBaseDataSource() {
		return getDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Primary
	@Bean(name = "baseTransactionManager")
	public DataSourceTransactionManager getBaseTransactionManager(
			@Qualifier("baseDataSource") DataSource baseDataSource) {
		return new DataSourceTransactionManager(baseDataSource);
	}

	@Primary
	@Bean(name = "baseSqlSessionFactory")
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("baseDataSource") DataSource baseDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(baseDataSource);
		return sessionFactory.getObject();
	}
}

package com.example.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Configuration
    public class DataSourceConfiguration {

        @Primary
        @Bean
        @ConfigurationProperties(prefix = "db1.datasource")
        public DataSource primaryDataSource() {
            return DataSourceBuilder.create().build();
        }

        @Bean
        @ConfigurationProperties(prefix = "db2.datasource")
        public DataSource secondaryDataSource() {
            return DataSourceBuilder.create().build();
        }

        @Bean
        public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
            return new JdbcTemplate(primaryDataSource);
        }

        @Bean
        public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
            return new JdbcTemplate(secondaryDataSource);
        }
    }
}
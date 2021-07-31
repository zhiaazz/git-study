package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

@SpringBootApplication
@Slf4j
public class DemoApplication {

//    @Autowired
//    private DataSource dataSource;test2
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplate primaryJdbcTemplate;

    @Autowired
    private JdbcTemplate secondaryJdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public void run(String... args) throws Exception {
        showDataDB1_2();
    }

//    private void showConnection() throws SQLException {
//        log.info(dataSource.toString());
//        Connection connection = dataSource.getConnection();
//        log.info(connection.toString());
//        connection.close();
//    }
//
//    private void showData() {
//        jdbcTemplate.queryForList("SELECT * FROM FOO")
//                .forEach(row -> log.info(row.toString()));
//    }

    private void showDataDB1_2() {
        // 往第一个数据源中插入 2 条数据
        primaryJdbcTemplate.update("insert into user(name,age) values(?, ?)", "aaa", 20);
        primaryJdbcTemplate.update("insert into user(name,age) values(?, ?)", "bbb", 30);

        // 往第二个数据源中插入 1 条数据，若插入的是第一个数据源，则会主键冲突报错
        secondaryJdbcTemplate.update("insert into user(name,age) values(?, ?)", "ccc", 20);

        primaryJdbcTemplate.queryForList("SELECT * FROM user;").forEach(row -> row.toString());
        secondaryJdbcTemplate.queryForList("SELECT * FROM user;").forEach(row -> row.toString());
    }
}

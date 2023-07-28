package com.luchkovskiy.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan("com.luchkovskiy")
public class ApplicationConfig {

    @Bean
    public DataSource dataSource(PropertiesManager propertiesManager) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(propertiesManager.getDriverName());
        dataSource.setJdbcUrl(propertiesManager.getUrl());
        dataSource.setUsername(propertiesManager.getLogin());
        dataSource.setPassword(propertiesManager.getPassword());
        dataSource.setMaximumPoolSize(propertiesManager.getPoolSize());
        return dataSource;
    }

}

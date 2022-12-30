package com.myapp.rest.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import javax.sql.DataSource;

import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.myapp.rest")
@EnableWebMvc
@PropertySource("classpath:hibernate_config.properties")
@EnableTransactionManagement
public class MyConfig {

    private final Environment environment;

    public MyConfig(Environment environment) {
        this.environment = environment;
    }


    @Bean
    public DataSource dataSource() {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(environment.getProperty("driver_class"));
            dataSource.setJdbcUrl(environment.getProperty("jdbc_url"));
            dataSource.setUser(environment.getProperty("user_name"));
            dataSource.setPassword(environment.getProperty("user_password"));
            dataSource.setDataSourceName("name");
        } catch (PropertyVetoException e) {
            System.out.println("*********** Не загружен driver_class или ошибка url, password, username ******");
            throw new RuntimeException(e);
        }

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean manager() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJpaVendorAdapter(jpaVendorAdapter());
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("com.myapp.rest.entity");
        entityManager.setJpaProperties(properties());

        return entityManager;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(manager().getObject());

        return jpaTransactionManager;
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));

        return properties;
    }

}

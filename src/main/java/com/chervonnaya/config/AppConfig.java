package com.chervonnaya.config;

import com.chervonnaya.dto.TaskDTO;
import com.chervonnaya.model.Status;
import com.chervonnaya.model.Task;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Properties;



@Configuration
@ComponentScan("com.chervonnaya")
@PropertySource("classpath:app.properties")
@EnableJpaRepositories(basePackages = "com.chervonnaya.dao")
@EnableTransactionManagement
public class AppConfig {
   private final Environment env;

    @Autowired
    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public HikariDataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
        hikariConfig.setUsername(env.getProperty("jdbc.user"));
        hikariConfig.setPassword(env.getProperty("jdbc.password"));
        hikariConfig.setDriverClassName(env.getProperty("jdbc.driver"));

        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("springHikariCP");

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("com.chervonnaya");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(hibernateProperties());
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);

        return transactionManager;
    }

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        Converter<String, Status> toStatusConverter = context ->
                context.getSource() == null ? null : Status.valueOf(context.getSource());

        Converter<Status, String> toStringConverter = context ->
                context.getSource() == null ? null : context.getSource().toString();

        modelMapper.typeMap(Task.class, TaskDTO.class)
                .addMappings(mapper -> mapper.using(toStringConverter).map(Task::getStatus, TaskDTO::setStatus));

        modelMapper.typeMap(TaskDTO.class, Task.class)
                .addMappings(mapper -> mapper.using(toStatusConverter).map(TaskDTO::getStatus, Task::setStatus));

        return modelMapper;
    }
}

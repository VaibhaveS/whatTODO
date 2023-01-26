package com.example.done.shards;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.done",
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef = "multiTransactionManager"
)
public class TodoDBConfig {

    private final String PACKAGE_SCAN = "com.example.done";

    private DataSource createDataSource(String username, String password, String url) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        dataSourceBuilder.url(url);
        return dataSourceBuilder.build();
    }
    @Primary
    @Bean(name = "PrimaryDataSource")
    public DataSource primaryDataSource() {
        return createDataSource("root", "rootroot", "jdbc:mysql://localhost:3306/shard");
    }

    @Bean
    @Primary
    public LiquibaseProperties primaryLiquibaseProperties() {
        return new LiquibaseProperties();
    }
    private static SpringLiquibase springLiquibase(DataSource dataSource, String changelog, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changelog);
        return liquibase;
    }

    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource() throws LiquibaseException {
        Properties tenantProperties = new Properties();
        File[] files = Paths.get("shardProp").toFile().listFiles();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("primary", primaryDataSource());
        TodoRoutingDataSource multiRoutingDataSource = new TodoRoutingDataSource();
        SpringLiquibase ls = springLiquibase(primaryDataSource(), "classpath:db.changelog/shard/changelog-master.xml", primaryLiquibaseProperties());
        multiRoutingDataSource.setDefaultTargetDataSource(primaryDataSource());
        ls.afterPropertiesSet();
        int shardId = 1;
        for(File propertyFile: files) {
            try {
                tenantProperties.load(new FileInputStream(propertyFile));
                DataSource ds = createDataSource(tenantProperties.getProperty("datasource.username"), tenantProperties.getProperty("datasource.password"), tenantProperties.getProperty("datasource.url"));
                targetDataSources.put("shard_"+ Integer.toString(shardId), ds);
                SpringLiquibase Shardls = springLiquibase(ds, "classpath:db.changelog/changelog-master.xml", primaryLiquibaseProperties());
                Shardls.afterPropertiesSet();
                shardId++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;
    }
    @Bean(name = "multiEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager() throws LiquibaseException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(multiRoutingDataSource());
        em.setPackagesToScan(PACKAGE_SCAN);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }
    @Bean(name = "multiTransactionManager")
    public PlatformTransactionManager multiTransactionManager() throws LiquibaseException {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                multiEntityManager().getObject());
        return transactionManager;
    }
    @Primary
    @Bean(name = "dbSessionFactory")
    public LocalSessionFactoryBean dbSessionFactory() throws LiquibaseException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource());
        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        return properties;
    }
}
package com.pivote.persistences;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Persistencia de enitidades para dbdatos
 */
@Configuration
@ComponentScan({"org.serfinsa.frameworks.models"})
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(entityManagerFactoryRef = "dbdatosEMF", transactionManagerRef = "dbdatosTMF"
        , basePackages = "org.serfinsa.frameworks.models.cardinfo.repositories")
public class DbDatosPersistence {

    private final String[] entityPackage = {"org.serfinsa.frameworks.models.cardinfo.entities"};
    private final String percistenceName = "cardinfoPersistence";

    @Bean(name = "dbdatosConfigurationProperties")
    @ConfigurationProperties(prefix = "dbdatos.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dbdatosDataSource")
    public DataSource getDataSource(@Qualifier("dbdatosConfigurationProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "dbdatosEMF")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder entityManager
            , @Qualifier("dbdatosDataSource") DataSource dataSource) {
        return entityManager.dataSource(dataSource).packages(this.entityPackage).persistenceUnit(this.percistenceName)
                .build();
    }

    @Bean(name = "dbdatosTMF")
    public PlatformTransactionManager transactionManager
            (@Qualifier("dbdatosEMF") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

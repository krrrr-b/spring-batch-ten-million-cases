package com.skeleton.batch.data.config

import com.skeleton.batch.data.config.datasource.HikariDataSourceConfig
import com.skeleton.batch.data.config.datasource.ReplicationRoutingDataSource
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource
import kotlin.collections.HashMap

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["com.skeleton.batch"])
class DatabaseConfiguration {
    companion object {
        private const val PERSISTENCE_UNIT_NAME = "batch"
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }

    @Bean
    fun dataSource(
        @Qualifier(HikariDataSourceConfig.DATABASE_HIKARI_SOURCE) dataSource: HikariDataSource,
    ): DataSource {
        val masterDataSource: DataSource = dataSource
        val slaveDataSource: DataSource = dataSource
        val dataSourceMap: MutableMap<Any, Any> = HashMap()

        dataSourceMap["slave"] = slaveDataSource
        dataSourceMap["master"] = masterDataSource

        val routingDataSource = ReplicationRoutingDataSource()
        routingDataSource.setTargetDataSources(dataSourceMap)
        routingDataSource.setDefaultTargetDataSource(masterDataSource)
        routingDataSource.afterPropertiesSet()

        return LazyConnectionDataSourceProxy(routingDataSource)
    }

    @Bean
    fun entityManagerFactory(
        dataSource: DataSource,
        jpaProperties: JpaProperties,
    ): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.dataSource = dataSource
        factory.setPackagesToScan("com.skeleton.batch")
        factory.persistenceUnitName = PERSISTENCE_UNIT_NAME

        val properties = Properties()
        properties.putAll(
            HibernateProperties()
                .determineHibernateProperties(
                    jpaProperties.properties,
                    HibernateSettings()
                )
        )

        factory.setJpaProperties(properties)

        return factory
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}

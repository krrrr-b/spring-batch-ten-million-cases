package com.skeleton.batch.data.config.datasource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class HikariDataSourceConfig(
    @Value("\${spring.datasource.driver-class-name}")
    private var driverClassName: String,

    @Value("\${spring.datasource.hikari.jdbc-url}")
    private var url: String,

    @Value("\${spring.datasource.hikari.username}")
    private var username: String,

    @Value("\${spring.datasource.hikari.password}")
    private var password: String,
) {
    companion object {
        const val DATABASE_HIKARI_SOURCE = "DATABASE_HIKARI_SOURCE"
    }

    @Primary
    @Bean(DATABASE_HIKARI_SOURCE)
    fun dataSource(): HikariDataSource {
        return HikariDataSource(HikariConfig()
            .let {
                it.driverClassName = driverClassName
                it.jdbcUrl = "jdbc:$url"
                it.username = username
                it.password = password
                it.connectionTestQuery = "select 1"
                it.connectionTimeout = 3000
                it.idleTimeout = 0
                it.maxLifetime = 170000

                it
            })
    }
}

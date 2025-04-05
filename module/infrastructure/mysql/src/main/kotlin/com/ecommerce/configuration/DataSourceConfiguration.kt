package com.ecommerce.configuration

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration {

    @Bean
    fun dataSource(properties: DataSourceProperties): DataSource {
        return HikariDataSource().apply {
            jdbcUrl = properties.url
            username = properties.username
            password = properties.password
            driverClassName = properties.driverClassName
        }
    }

}
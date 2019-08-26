package com.example.blog

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource

class KPostgresContainer(imageName: String) : PostgreSQLContainer<KPostgresContainer>(imageName)

@Configuration
class TestcontainersConfig {

    @Bean
    fun postgreSQLContainer(): KPostgresContainer {
        val postgreSQLContainer = KPostgresContainer("postgres")
                .withDatabaseName("foo")
                .withUsername("foo")
                .withPassword("secret")
        postgreSQLContainer.start()
        return postgreSQLContainer
    }

    @Bean
    fun dataSource(postgreSQLContainer: KPostgresContainer): DataSource {
        return DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.driverClassName)
                .url(postgreSQLContainer.jdbcUrl)
                .username(postgreSQLContainer.username)
                .password(postgreSQLContainer.password)
                .build()
    }

}
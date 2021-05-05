package com.skeleton.batch.configuration

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
class BatchConfiguration {
    @Bean
    fun jobRepository() =
        MapJobRepositoryFactoryBean(ResourcelessTransactionManager()).getObject()

    @Bean
    fun jobLauncher(jobRepository: JobRepository) =
        SimpleJobLauncher().also { it.setJobRepository(jobRepository) }
}

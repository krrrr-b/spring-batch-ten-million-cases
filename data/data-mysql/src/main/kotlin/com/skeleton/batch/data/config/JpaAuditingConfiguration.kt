package com.skeleton.batch.data.config

import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaAuditingConfiguration {
    @Bean
    fun auditorAware(): AuditorAware<String> {
        return AuthAuditorAware()
    }
}

package com.skeleton.batch.authentication

import com.skeleton.batch.util.BatchLoggerObject.batchJobCompleted
import com.skeleton.batch.util.BatchLoggerObject.batchJobFailed
import com.skeleton.batch.util.BatchLoggerObject.batchJobStopped
import com.skeleton.batch.authentication.step.AuthenticationStepConfiguration.Companion.AUTHENTICATION_EXPIRED_STEP
import com.skeleton.batch.validator.StringJobParametersValidator
import org.springframework.batch.core.BatchStatus.*
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepExecution
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthenticationJobConfiguration {
    companion object {
        const val AUTHENTICATION_EXPIRED_JOB = "authenticationExpiredJob"
    }

    @Bean(AUTHENTICATION_EXPIRED_JOB)
    fun authenticationExpiredJob(
        @Qualifier(AUTHENTICATION_EXPIRED_STEP) authenticationExpiredStep: Step,
        stringJobParametersValidator: StringJobParametersValidator,
        jobBuilderFactory: JobBuilderFactory,
    ): Job {
        return jobBuilderFactory[AUTHENTICATION_EXPIRED_JOB]
            .incrementer(RunIdIncrementer())
            .start(authenticationExpiredStep)
            .on(FAILED.name).to { jobExecution: JobExecution, _: StepExecution? ->
                batchJobFailed(jobExecution)
            }
            .on(STOPPED.name).to { jobExecution: JobExecution, _: StepExecution? ->
                batchJobStopped(jobExecution)
            }
            .on(COMPLETED.name).to { jobExecution: JobExecution, _: StepExecution? ->
                batchJobCompleted(jobExecution)
            }
            .from(authenticationExpiredStep)
            .on(COMPLETED.name)
            .end()
            .end()
            .build()
    }
}

package com.skeleton.batch.user

import com.skeleton.batch.util.BatchLoggerObject.batchJobFailed
import com.skeleton.batch.util.BatchLoggerObject.batchJobStopped
import com.skeleton.batch.user.step.최근_30일_동안_거래가_없는_회원_휴면_처리_스텝
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

const val 최근_30일_동안_거래가_없는_회원_휴면_처리_잡 = "statusChangeUserWithoutTransactionThirtyDaysJob"

@Configuration
class UserStatusChangeJobConfiguration {
    @Bean(최근_30일_동안_거래가_없는_회원_휴면_처리_잡)
    fun statusChangeUserWithoutTransactionThirtyDaysJob(
        @Qualifier(최근_30일_동안_거래가_없는_회원_휴면_처리_스텝) step: Step,
        stringJobParametersValidator: StringJobParametersValidator,
        jobBuilderFactory: JobBuilderFactory,
    ): Job {
        return jobBuilderFactory[최근_30일_동안_거래가_없는_회원_휴면_처리_스텝]
            .incrementer(RunIdIncrementer())
            .start(step)
            .on(FAILED.name).to { jobExecution: JobExecution, _: StepExecution? -> batchJobFailed(jobExecution) }
            .on(STOPPED.name).to { jobExecution: JobExecution, _: StepExecution? -> batchJobStopped(jobExecution) }
            .from(step)
            .on(COMPLETED.name)
            .end()
            .end()
            .build()
    }
}

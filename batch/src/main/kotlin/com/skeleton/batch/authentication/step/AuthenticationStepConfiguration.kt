package com.skeleton.batch.authentication.step

import com.skeleton.batch.authentication.step.processor.AUTHENTICATION_EXPIRED_PROCESSOR
import com.skeleton.batch.authentication.step.reader.AuthenticationItemReader.Companion.AUTHENTICATION_EXPIRED_READER
import com.skeleton.batch.authentication.step.writer.AUTHENTICATION_EXPIRED_WRITER
import com.skeleton.batch.configuration.TaskExecutorConfig
import com.skeleton.batch.configuration.TaskExecutorConfig.Companion.BATCH_RETRY_COUNT
import com.skeleton.batch.configuration.TaskExecutorConfig.Companion.CHUNK_PAGE_SIZE
import com.skeleton.batch.data.mysql.users.Users
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.dao.DeadlockLoserDataAccessException
import org.springframework.transaction.PlatformTransactionManager
import java.util.concurrent.TimeoutException

@Configuration
class AuthenticationStepConfiguration(
    @Qualifier(TaskExecutorConfig.TASK_ASYNC_EXECUTOR)
    private val taskAsyncExecutor: TaskExecutor,
    private val transactionManager: PlatformTransactionManager,
) {
    companion object {
        const val AUTHENTICATION_EXPIRED_STEP = "AUTHENTICATION_EXPIRED_STEP"
    }

    @Bean(AUTHENTICATION_EXPIRED_STEP)
    fun authenticationExpiredStep(
        @Qualifier(AUTHENTICATION_EXPIRED_READER) reader: ItemReader<Users>,
        @Qualifier(AUTHENTICATION_EXPIRED_PROCESSOR) processor: ItemProcessor<Users, Users>,
        @Qualifier(AUTHENTICATION_EXPIRED_WRITER) writer: ItemWriter<Users>,
        stepBuilderFactory: StepBuilderFactory,
    ): Step {
        return stepBuilderFactory
            .get(AUTHENTICATION_EXPIRED_STEP)
            .chunk<Users, Users>(CHUNK_PAGE_SIZE)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .faultTolerant()
            .retryLimit(BATCH_RETRY_COUNT)
            .retry(TimeoutException::class.java)
            .retry(DeadlockLoserDataAccessException::class.java)
            .transactionManager(transactionManager)
            .taskExecutor(taskAsyncExecutor)
            .build()
    }
}

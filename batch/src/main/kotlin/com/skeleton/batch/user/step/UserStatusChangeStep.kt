package com.skeleton.batch.user.step

import com.skeleton.batch.configuration.TaskExecutorConfiguration
import com.skeleton.batch.configuration.TaskExecutorConfiguration.Companion.BATCH_RETRY_COUNT
import com.skeleton.batch.configuration.TaskExecutorConfiguration.Companion.CHUNK_PAGE_SIZE
import com.skeleton.batch.configuration.TaskExecutorConfiguration.Companion.THROTTLE_LIMIT_SIZE
import com.skeleton.batch.data.mysql.users.Users
import com.skeleton.batch.user.step.processor.여신_대출_존재하지_않는_유저_휴면_처리_프로세서
import com.skeleton.batch.user.step.reader.최근_30일_동안_거래가_없는_회원_조회_리더
import com.skeleton.batch.user.step.writer.유저_데이터_저장_라이터
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

const val 최근_30일_동안_거래가_없는_회원_휴면_처리_스텝 = "statusChangeUserWithoutTransactionThirtyDaysStep"

@Configuration
class UserStatusChangeStep(
    @Qualifier(TaskExecutorConfiguration.TASK_ASYNC_EXECUTOR)
    private val taskAsyncExecutor: TaskExecutor,
    private val transactionManager: PlatformTransactionManager,
) {
    @Bean(최근_30일_동안_거래가_없는_회원_휴면_처리_스텝)
    fun statusChangeUserWithoutTransactionThirtyDaysStep(
        @Qualifier(최근_30일_동안_거래가_없는_회원_조회_리더) reader: ItemReader<Users>,
        @Qualifier(여신_대출_존재하지_않는_유저_휴면_처리_프로세서) processor: ItemProcessor<Users, Users>,
        @Qualifier(유저_데이터_저장_라이터) writer: ItemWriter<Users>,
        stepBuilderFactory: StepBuilderFactory,
    ): Step {
        return stepBuilderFactory
            .get(최근_30일_동안_거래가_없는_회원_휴면_처리_스텝)
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
            .throttleLimit(THROTTLE_LIMIT_SIZE)
            .build()
    }
}

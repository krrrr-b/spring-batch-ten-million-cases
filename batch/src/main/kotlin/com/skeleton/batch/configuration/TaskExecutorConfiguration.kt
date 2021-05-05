package com.skeleton.batch.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy

@Configuration
class TaskExecutorConfiguration {
    companion object {
        const val TASK_ASYNC_EXECUTOR = "BATCH_TASK_ASYNC_EXECUTOR"

        const val QUEUE_CAPACITY_SIZE = 25
        const val CHUNK_PAGE_SIZE = 1000
        const val BATCH_RETRY_COUNT = 3
        const val THROTTLE_LIMIT_SIZE = 50
        const val CORE_POOL_SIZE = 50
        const val MAX_POOL_SIZE = 50
    }

    @Bean(TASK_ASYNC_EXECUTOR)
    fun taskAsyncExecutor() =
        ThreadPoolTaskExecutor()
            .also {
                it.maxPoolSize = MAX_POOL_SIZE
                it.corePoolSize = CORE_POOL_SIZE
                it.setQueueCapacity(QUEUE_CAPACITY_SIZE)
                it.setRejectedExecutionHandler(CallerRunsPolicy())
                it.initialize()
            }
}

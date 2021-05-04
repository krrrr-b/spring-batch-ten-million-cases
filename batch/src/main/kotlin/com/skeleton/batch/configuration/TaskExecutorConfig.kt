package com.skeleton.batch.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy

@Configuration
class TaskExecutorConfig {
    companion object {
        const val TASK_ASYNC_EXECUTOR = "BATCH_TASK_ASYNC_EXECUTOR"

        const val QUEUE_CAPACITY_SIZE = 25
        const val CHUNK_PAGE_SIZE = 500
        const val BATCH_RETRY_COUNT = 3
        const val CORE_POOL_SIZE = 10
        const val MAX_POOL_SIZE = 50
    }

    @Bean(TASK_ASYNC_EXECUTOR)
    fun taskAsyncExecutor(): TaskExecutor {
        val threadPoolTaskExecutor = ThreadPoolTaskExecutor()
        threadPoolTaskExecutor.maxPoolSize = MAX_POOL_SIZE
        threadPoolTaskExecutor.corePoolSize = CORE_POOL_SIZE
        threadPoolTaskExecutor.setQueueCapacity(QUEUE_CAPACITY_SIZE)
        threadPoolTaskExecutor.setRejectedExecutionHandler(CallerRunsPolicy())
        threadPoolTaskExecutor.initialize()
        return threadPoolTaskExecutor
    }
}

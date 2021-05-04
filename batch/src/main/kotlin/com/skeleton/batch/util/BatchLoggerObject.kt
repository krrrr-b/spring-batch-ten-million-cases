package com.skeleton.batch.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.job.flow.FlowExecutionStatus

object BatchLoggerObject {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun batchJobCompleted(jobExecution: JobExecution): FlowExecutionStatus {
        logger.info(
            "$this ${jobExecution.jobId} 배치가 완료되었습니다." +
                    "\n job_parameters: ${jobExecution.jobParameters}, " +
                    "\n create_time: ${jobExecution.createTime}, " +
                    "\n end_time: ${jobExecution.endTime}, " +
                    "<@here>", jobExecution.allFailureExceptions
        )

        return FlowExecutionStatus(BatchStatus.COMPLETED.name)
    }

    fun batchJobFailed(jobExecution: JobExecution): FlowExecutionStatus {
        logger.error(
            "$this ${jobExecution.jobId} 배치가 실패하였습니다." +
                    "\n job_parameters: ${jobExecution.jobParameters}, " +
                    "\n create_time: ${jobExecution.createTime}, " +
                    "\n end_time: ${jobExecution.endTime}, " +
                    "<@here>", jobExecution.allFailureExceptions
        )

        return FlowExecutionStatus(BatchStatus.FAILED.name)
    }

    fun batchJobStopped(jobExecution: JobExecution): FlowExecutionStatus {
        logger.error(
            "$this ${jobExecution.jobId} 배치가 중단되었습니다." +
                    "\n job_parameters: ${jobExecution.jobParameters}, " +
                    "\n create_time: ${jobExecution.createTime}, " +
                    "\n end_time: ${jobExecution.endTime}, " +
                    "<@here>"
        )

        return FlowExecutionStatus(BatchStatus.STOPPED.name)
    }
}

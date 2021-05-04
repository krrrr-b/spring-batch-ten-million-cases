package com.skeleton.batch

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import kotlin.system.exitProcess
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.WebApplicationType

@SpringBootApplication(scanBasePackages = ["com.skeleton.batch"])
class BatchApplication {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            if (args.size < 2) {
                return
            }

            System.setProperty("spring.profiles.active", args[0])
            val context = SpringApplication(BatchApplication::class.java)
                .also {
                    it.webApplicationType = WebApplicationType.NONE
                }
                .run(*args)

            logger.info("$this, profile: ${args[0]} parameter is ${args[1]}")

            context.getBean(JobLauncher::class.java)
                .run(context.getBean(String(args[1].toCharArray()), Job::class.java), JobParametersBuilder()
                    .addString("time", System.currentTimeMillis().toString())
                    .toJobParameters())

            exitProcess(0)
        }
    }
}

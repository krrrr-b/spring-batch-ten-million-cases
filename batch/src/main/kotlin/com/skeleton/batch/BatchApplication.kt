package com.skeleton.batch

import com.skeleton.batch.data.mysql.configuration.DataLoader
import com.skeleton.batch.data.mysql.users.UsersRepository
import com.skeleton.batch.user.최근_30일_동안_거래가_없는_회원_휴면_처리_잡
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(scanBasePackages = ["com.skeleton.batch"])
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}

@RestController
data class SimpleJobLauncher(
    private val jobLauncher: SimpleJobLauncher,
    private val usersRepository: UsersRepository,

    @Qualifier(최근_30일_동안_거래가_없는_회원_휴면_처리_잡)
    private val springBatchJob: Job
) {
    @GetMapping("users/create")
    fun createUser() = DataLoader(usersRepository).createUser(1000000)

    @GetMapping("spring-batch/status-change-user-without-transaction-thirty-days-Job")
    fun springBatchJob() {
        jobLauncher.run(
            springBatchJob, JobParametersBuilder()
                .addString("time", System.currentTimeMillis().toString())
                .toJobParameters()
        )
    }

    @GetMapping("batch/status-change-user-without-transaction-thirty-days-Job")
    fun justNormalBatch() {
        val startTime = System.currentTimeMillis()

        usersRepository
            .getUserByLessThenTransactionCountFiveAndAmountOneHundredThousandBeforeThirtyDays()
            .filter { !isExistsLoans(it.id!!) }
            // @description one by one, not bulk
            .map {
                it.setDormant()
                usersRepository.save(it)
            }
            .toList()

        val milliseconds = System.currentTimeMillis() - startTime
        val seconds = (milliseconds / 1000) % 60

        println("${seconds}s${milliseconds % 1000}ms")
    }

    private fun isExistsLoans(userId: Long): Boolean {
        return false
    }
}

/**
 * 배치 어플리케이션 파일로 실행시키기 위한 설정
 * 시연을 위해 주석 처리
 */
//@SpringBootApplication(scanBasePackages = ["com.skeleton.batch"])
//class BatchApplication {
//    companion object {
//        private val logger: Logger = LoggerFactory.getLogger(this::class.java)
//
//        @JvmStatic
//        fun main(args: Array<String>) {
//            if (args.size < 2) {
//                return
//            }
//
//            System.setProperty("spring.profiles.active", args[0])
//            val context = SpringApplication(BatchApplication::class.java)
//                .also {
//                    it.webApplicationType = WebApplicationType.NONE
//                }
//                .run(*args)
//
//            logger.info("$this, profile: ${args[0]} parameter is ${args[1]}")
//
//            context.getBean(JobLauncher::class.java)
//                .run(context.getBean(String(args[1].toCharArray()), Job::class.java), JobParametersBuilder()
//                    .addString("time", System.currentTimeMillis().toString())
//                    .toJobParameters())
//
//            exitProcess(0)
//        }
//    }
//}

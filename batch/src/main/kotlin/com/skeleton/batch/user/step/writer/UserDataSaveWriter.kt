package com.skeleton.batch.user.step.writer

import com.skeleton.batch.data.mysql.users.Users
import com.skeleton.batch.data.mysql.users.UsersRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

const val 유저_데이터_저장_라이터 = "userStatusChangeDormantWriter"

@Component(유저_데이터_저장_라이터)
class UserDataSaveWriter(
    private val usersRepository: UsersRepository,
) : ItemWriter<Users> {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun write(entities: List<Users>) {
        logger.info("$this save entities count: ${entities.size} list: $entities")
        usersRepository.saveAll(entities)
    }
}

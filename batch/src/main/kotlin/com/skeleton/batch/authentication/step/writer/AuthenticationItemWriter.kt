package com.skeleton.batch.authentication.step.writer

import com.skeleton.batch.data.mysql.users.Users
import com.skeleton.batch.data.mysql.users.UsersRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

const val AUTHENTICATION_EXPIRED_WRITER = "AUTHENTICATION_EXPIRED_WRITER"

@Component(AUTHENTICATION_EXPIRED_WRITER)
class AuthenticationItemWriter(
    private val usersRepository: UsersRepository,
) : ItemWriter<Users> {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun write(entities: List<Users>) {
        logger.info("$this entities: $entities")
        usersRepository.saveAll(entities)
    }
}

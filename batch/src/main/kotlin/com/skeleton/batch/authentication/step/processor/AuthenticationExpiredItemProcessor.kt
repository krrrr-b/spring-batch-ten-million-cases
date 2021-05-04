package com.skeleton.batch.authentication.step.processor

import com.skeleton.batch.data.mysql.users.Users
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

const val AUTHENTICATION_EXPIRED_PROCESSOR = "AUTHENTICATION_EXPIRED_READER"

@Component(AUTHENTICATION_EXPIRED_PROCESSOR)
class AuthenticationExpiredItemProcessor : ItemProcessor<Users, Users> {
    override fun process(entity: Users) = entity
}

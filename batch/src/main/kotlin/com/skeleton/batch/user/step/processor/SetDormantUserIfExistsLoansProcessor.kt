package com.skeleton.batch.user.step.processor

import com.skeleton.batch.data.mysql.users.Users
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

const val 여신_대출_존재하는_유저_휴면_처리_프로세서 = "setDormantUserIfExistsLoansProcessor"

@Component(여신_대출_존재하는_유저_휴면_처리_프로세서)
class SetDormantUserIfExistsLoansProcessor : ItemProcessor<Users, Users> {
    override fun process(entity: Users): Users? {
        return if (isExistsLoans(entity.id!!)) null
        else entity.also { it.setDormant() }
    }

    private fun isExistsLoans(userId: Long): Boolean {
        return false
    }
}

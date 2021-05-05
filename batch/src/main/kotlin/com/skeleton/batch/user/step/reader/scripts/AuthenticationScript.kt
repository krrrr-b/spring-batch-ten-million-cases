package com.skeleton.batch.user.step.reader.scripts

import com.skeleton.batch.data.mysql.users.USERS_TABLE
import com.skeleton.batch.data.mysql.users.UserStatus
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider
import java.util.*

object AuthenticationScript {
    fun findUserByLessThenTransactionCountFiveAndAmountOneHundredThousandBeforeThirtyDaysScripts() =
        MySqlPagingQueryProvider()
            .also {
                it.setSelectClause("*".trimIndent())
                it.setFromClause("$USERS_TABLE u")
                it.setWhereClause("" +
                        "u.status = '${UserStatus.NORMAL}' AND " +
                        "(SELECT COUNT(1) " +
                        " FROM transactions t " +
                        " WHERE t.user_id = u.id " +
                        "   AND t.amount >= 1000000 " +
                        "   AND t.created_at >= (NOW() - INTERVAL 1 MONTH)) <= 5")
                it.sortKeys = Collections.singletonMap("id", Order.DESCENDING)
            }
}

package com.skeleton.batch.authentication.step.reader.scripts

import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.PagingQueryProvider
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider
import java.util.*

object AuthenticationScript {
    fun findUsersScripts(): PagingQueryProvider {
        return MySqlPagingQueryProvider()
            .also {
                it.setSelectClause("*".trimIndent())
                it.setFromClause("users u")
                it.setWhereClause("u.name IS NOT NULL AND u.updated_at <= NOW()")
                it.sortKeys = Collections.singletonMap("id", Order.DESCENDING)
            }
    }
}

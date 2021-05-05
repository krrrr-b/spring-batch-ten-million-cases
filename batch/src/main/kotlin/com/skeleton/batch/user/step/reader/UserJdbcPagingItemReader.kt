package com.skeleton.batch.user.step.reader

import com.skeleton.batch.user.step.reader.scripts.AuthenticationScript
import com.skeleton.batch.configuration.TaskExecutorConfiguration.Companion.CHUNK_PAGE_SIZE
import com.skeleton.batch.data.mysql.users.UserStatus
import com.skeleton.batch.data.mysql.users.Users
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.context.annotation.Bean
import java.sql.ResultSet
import org.springframework.stereotype.Component
import javax.sql.DataSource

const val 최근_30일_동안_거래가_없는_회원_조회_리더 =
    "findUserByLessThenTransactionCountFiveAndAmountOneHundredThousandBeforeThirtyDays"

@Component
class UserReader {
    @StepScope
    @Bean(최근_30일_동안_거래가_없는_회원_조회_리더)
    fun findUserByLessThenTransactionCountFiveAndAmountOneHundredThousandBeforeThirtyDays(
        dataSource: DataSource,
    ): JdbcPagingItemReader<Users> {
        val reader: JdbcPagingItemReader<Users> = JdbcPagingItemReader<Users>()
        reader.setDataSource(dataSource)
        reader.setQueryProvider(AuthenticationScript.findUserByLessThenTransactionCountFiveAndAmountOneHundredThousandBeforeThirtyDaysScripts())
        reader.pageSize = CHUNK_PAGE_SIZE
        reader.setRowMapper { rs: ResultSet, _: Int ->
            Users(
                UserStatus.findByName(rs.getString("status"))
            ).also {
                it.id = rs.getLong("id")
            }
        }
        reader.afterPropertiesSet()
        reader.isSaveState = false

        return reader
    }
}

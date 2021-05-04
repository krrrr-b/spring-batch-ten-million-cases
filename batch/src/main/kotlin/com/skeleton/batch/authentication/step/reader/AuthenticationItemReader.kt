package com.skeleton.batch.authentication.step.reader

import com.skeleton.batch.authentication.step.reader.scripts.AuthenticationScript
import com.skeleton.batch.configuration.TaskExecutorConfig.Companion.CHUNK_PAGE_SIZE
import com.skeleton.batch.data.mysql.users.Users
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.context.annotation.Bean
import java.sql.ResultSet
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class AuthenticationItemReader {
    companion object {
        const val AUTHENTICATION_EXPIRED_READER = "authenticationExpiredItemReader"
    }

    @StepScope
    @Bean(AUTHENTICATION_EXPIRED_READER)
    fun authenticationExpiredItemReader(
        dataSource: DataSource,
    ): JdbcPagingItemReader<Users> {
        val reader: JdbcPagingItemReader<Users> = JdbcPagingItemReader<Users>()
        reader.setDataSource(dataSource)
        reader.setQueryProvider(AuthenticationScript.findUsersScripts())
        reader.pageSize = CHUNK_PAGE_SIZE
        reader.setRowMapper { rs: ResultSet, _: Int ->
            Users(
                rs.getString("name"),
            ).also {
                it.id = rs.getLong("id")
            }
        }
        reader.afterPropertiesSet()

        return reader
    }
}

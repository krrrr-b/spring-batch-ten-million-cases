package com.skeleton.batch.data.mysql.users

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : JpaRepository<Users, Long> {
    @Query(value = "SELECT * FROM $USERS_TABLE u WHERE u.status = 'NORMAL' AND (SELECT COUNT(1) FROM transactions t WHERE t.user_id = u.id AND t.amount >= 1000000 AND t.created_at >= (NOW() - INTERVAL 1 MONTH)) <= 5", nativeQuery = true)
    fun getUserByLessThenTransactionCountFiveAndAmountOneHundredThousandBeforeThirtyDays(): List<Users>
}

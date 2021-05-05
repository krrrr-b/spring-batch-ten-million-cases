package com.skeleton.batch.data.mysql.transactions

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionsRepository : JpaRepository<Transactions, Long> {
}

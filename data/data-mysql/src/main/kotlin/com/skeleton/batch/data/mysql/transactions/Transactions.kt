package com.skeleton.batch.data.mysql.transactions

import com.skeleton.batch.data.mysql.BaseTimeEntity
import com.skeleton.batch.data.mysql.users.Users
import lombok.NoArgsConstructor
import java.math.BigDecimal
import javax.persistence.*

const val TRANSACTIONS_TABLE = "transactions"

@Entity
@NoArgsConstructor
@Table(name = TRANSACTIONS_TABLE)
data class Transactions(
    @ManyToOne
    val user: Users,

    @Column
    val amount: BigDecimal
) : BaseTimeEntity()

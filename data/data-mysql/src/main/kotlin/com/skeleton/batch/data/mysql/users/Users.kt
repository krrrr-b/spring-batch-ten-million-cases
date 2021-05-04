package com.skeleton.batch.data.mysql.users

import com.skeleton.batch.data.mysql.BaseTimeEntity
import org.hibernate.envers.Audited
import javax.persistence.*

const val USERS_TABLE = "users"

@Entity
@Audited
@Table(name = USERS_TABLE)
data class Users(
    val name: String
) : BaseTimeEntity()

package com.skeleton.batch.data.mysql.users

import com.skeleton.batch.data.mysql.BaseTimeEntity
import javax.persistence.*

const val USERS_TABLE = "users"

@Entity
@Table(name = USERS_TABLE)
data class Users(
    @Enumerated(EnumType.STRING)
    var status: UserStatus
) : BaseTimeEntity() {
    // @todo. kotlin jpa, all-open script
    constructor() : this(UserStatus.NORMAL)

    fun isNormalStatus() = UserStatus.NORMAL == status
    fun setDormant(): Users {
        status = UserStatus.DORMANT
        return this
    }

    override fun toString(): String {
        return "Users(id=${this.id}, status=${status})"
    }
}

enum class UserStatus {
    NORMAL,
    DORMANT;

    companion object {
        fun findByName(name: String): UserStatus {
            return values().first { it.name == name }
        }
    }
}

package com.skeleton.batch.data.mysql.configuration

import com.skeleton.batch.data.mysql.users.Users
import com.skeleton.batch.data.mysql.users.UsersRepository

class DataLoader(
    private val usersRepository: UsersRepository
) {
    fun createUser(userCount: Int): MutableList<Users> {
        return usersRepository.saveAll(testUsers(userCount))
    }

    private fun testUsers(userCount: Int): MutableList<Users> {
        val users: MutableList<Users> = mutableListOf()
        for (i in 1..userCount) {
            users.add(Users())
        }

        return users
    }
}

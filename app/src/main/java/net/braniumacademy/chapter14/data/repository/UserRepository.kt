package net.braniumacademy.chapter14.data.repository

import kotlinx.coroutines.flow.Flow
import net.braniumacademy.chapter14.data.model.User

interface UserRepository {
    val users: Flow<List<User>>

    suspend fun save(user: User): Long
}
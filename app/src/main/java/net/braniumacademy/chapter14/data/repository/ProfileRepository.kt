package net.braniumacademy.chapter14.data.repository

import kotlinx.coroutines.flow.Flow
import net.braniumacademy.chapter14.data.model.Profile

interface ProfileRepository {
    fun login(email: String, password: String): Flow<Profile?>
    fun findUserById(id: Int): Flow<Profile?>
}
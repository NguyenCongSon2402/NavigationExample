package net.braniumacademy.chapter14.data.repository

import kotlinx.coroutines.flow.Flow
import net.braniumacademy.chapter14.data.dao.UserDao
import net.braniumacademy.chapter14.data.model.Profile
import net.braniumacademy.chapter14.data.model.User

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override val users: Flow<List<User>>
        get() = userDao.findAll()

    override suspend fun save(user: User): Long {
        return userDao.insert(user)
    }
}
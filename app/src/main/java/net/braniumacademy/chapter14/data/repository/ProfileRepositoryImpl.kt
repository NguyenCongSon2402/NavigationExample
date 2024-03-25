package net.braniumacademy.chapter14.data.repository

import kotlinx.coroutines.flow.Flow
import net.braniumacademy.chapter14.data.dao.ProfileDao
import net.braniumacademy.chapter14.data.model.Profile

class ProfileRepositoryImpl(
    private val profileDao: ProfileDao
) : ProfileRepository {
    override fun login(email: String, password: String): Flow<Profile?> {
        return profileDao.login(email, password)
    }

    override fun findUserById(id: Int): Flow<Profile?> {
        return profileDao.findById(id)
    }
}
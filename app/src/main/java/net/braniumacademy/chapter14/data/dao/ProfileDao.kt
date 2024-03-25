package net.braniumacademy.chapter14.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import net.braniumacademy.chapter14.data.model.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles WHERE id = :id")
    fun findById(id: Int): Flow<Profile?>

    @Query("SELECT * FROM profiles WHERE email = :email AND password = :password")
    fun login(email: String, password: String): Flow<Profile?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: Profile): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(user: Profile): Int

    @Delete
    suspend fun delete(user: Profile): Int
}
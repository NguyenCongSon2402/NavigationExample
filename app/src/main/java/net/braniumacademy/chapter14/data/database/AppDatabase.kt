package net.braniumacademy.chapter14.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import net.braniumacademy.chapter14.data.dao.ProfileDao
import net.braniumacademy.chapter14.data.dao.UserDao
import net.braniumacademy.chapter14.data.model.Profile
import net.braniumacademy.chapter14.data.model.User
import java.util.Date

@Database(
    entities = [Profile::class, User::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2, spec = AppDatabase.AutoMigration::class)]
)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    @RenameTable("users", "profiles")
    class AutoMigration : AutoMigrationSpec

    abstract fun getUserDao(): UserDao
    abstract fun getProfileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}

class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
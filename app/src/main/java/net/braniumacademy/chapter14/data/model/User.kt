package net.braniumacademy.chapter14.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String? = null,
    @ColumnInfo("full_name") val fullName: String? = null,
    val avatar: Int = 0
)

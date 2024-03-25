package net.braniumacademy.chapter14.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "profiles")
data class Profile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("full_name") val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val gender: String? = "",
    @ColumnInfo("phone_number") val phoneNumber: String? = null,
    val avatar: Int? = null,
    @ColumnInfo("birth_date", defaultValue = "NULL") val birthDate: Date? = null
)

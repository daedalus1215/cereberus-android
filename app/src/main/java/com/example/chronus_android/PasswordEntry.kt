package com.example.chronus_android;
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntry(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val website: String,
        val username: String,
        val encryptedPassword: String
)

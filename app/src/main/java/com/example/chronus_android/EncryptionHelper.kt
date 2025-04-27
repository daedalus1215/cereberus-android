package com.example.chronus_android

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

object EncryptionHelper {
    private const val ALGORITHM = "AES"
    private const val PREFS_NAME = "secure_prefs"
    private const val MASTER_KEY = "master_key"

    // Initialize master key in EncryptedSharedPreferences
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMasterKey(context: Context): String {
        val sharedPrefs = EncryptedSharedPreferences.create(
            PREFS_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPrefs.getString(MASTER_KEY, null) ?: run {
            val newKey = Base64.getEncoder().encodeToString(generateKey())
            sharedPrefs.edit().putString(MASTER_KEY, newKey).apply()
            newKey
        }
    }

    private fun generateKey(): ByteArray {
        return ByteArray(16).apply { java.util.Random().nextBytes(this) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(data: String, key: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val secretKey = SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encrypted = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(data: String, key: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val secretKey = SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decrypted = cipher.doFinal(Base64.getDecoder().decode(data))
        return String(decrypted)
    }
}
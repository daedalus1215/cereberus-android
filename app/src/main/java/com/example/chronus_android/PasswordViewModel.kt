package com.example.chronus_android

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = DatabaseProvider.getDatabase(application).passwordDao()
    @RequiresApi(Build.VERSION_CODES.O)
    private val masterKey = EncryptionHelper.getMasterKey(application)
    private val _passwords = MutableStateFlow<List<PasswordEntry>>(emptyList())
    val passwords: StateFlow<List<PasswordEntry>> = _passwords

    init {
        loadPasswords()
    }

    private fun loadPasswords() {
        viewModelScope.launch {
            _passwords.value = dao.getAll()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addPassword(website: String, username: String, password: String) {
        viewModelScope.launch {
            val encryptedPassword = EncryptionHelper.encrypt(password, masterKey)
            val entry = PasswordEntry(
                website = website,
                username = username,
                encryptedPassword = encryptedPassword
            )
            dao.insert(entry)
            loadPasswords()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptPassword(encryptedPassword: String): String {
        return EncryptionHelper.decrypt(encryptedPassword, masterKey)
    }
}
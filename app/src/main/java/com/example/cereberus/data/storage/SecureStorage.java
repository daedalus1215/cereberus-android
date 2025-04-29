package com.example.cereberus.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.cereberus.data.model.PasswordEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class SecureStorage {

    private static final String FILE_NAME = "secure_passwords";
    private static final String KEY_PASSWORDS = "password_list";

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public SecureStorage(Context context) throws GeneralSecurityException, IOException {
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        sharedPreferences = EncryptedSharedPreferences.create(
                context,
                FILE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public List<PasswordEntry> loadPasswords() {
        String json = sharedPreferences.getString(KEY_PASSWORDS, null);
        if (json == null) return new ArrayList<>();

        Type listType = new TypeToken<ArrayList<PasswordEntry>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    public void savePasswords(List<PasswordEntry> passwords) {
        String json = gson.toJson(passwords);
        sharedPreferences.edit().putString(KEY_PASSWORDS, json).apply();
    }
}

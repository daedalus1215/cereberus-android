package com.example.cereberus.ui.vault;

import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.cereberus.R;
import com.example.cereberus.data.model.PasswordEntry;
import com.example.cereberus.data.storage.SecureStorage;
import com.example.cereberus.ui.util.InsetsUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class VaultActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> addPasswordLauncher;
    private SecureStorage secureStorage;
    private RecyclerView recyclerView;
    private PasswordAdapter adapter;
    private ArrayList<PasswordEntry> passwordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        InsetsUtil.applyTopInsets(findViewById(R.id.recyclerViewPasswords));

        try {
            secureStorage = new SecureStorage(this);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            finish(); // or show an error to the user
            return;
        }

        recyclerView = findViewById(R.id.recyclerViewPasswords);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load existing passwords
        List<PasswordEntry> loadedPasswords = secureStorage.loadPasswords();
        passwordList = new ArrayList<>(loadedPasswords); // make mutable copy

        adapter = new PasswordAdapter(passwordList);
        recyclerView.setAdapter(adapter);

        // Register the launcher for adding passwords
        addPasswordLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String service = data.getStringExtra("service");
                        String username = data.getStringExtra("username");
                        String password = data.getStringExtra("password");
                        String notes = data.getStringExtra("notes");

                        PasswordEntry newEntry = new PasswordEntry(service, username, password, notes);
                        passwordList.add(newEntry);
                        adapter.notifyItemInserted(passwordList.size() - 1);

                        // Save updated list
                        secureStorage.savePasswords(passwordList);
                    }
                }
        );

        FloatingActionButton fabAddPassword = findViewById(R.id.fabAddPassword);
        fabAddPassword.setOnClickListener(v -> {
            Intent intent = new Intent(VaultActivity.this, AddPasswordActivity.class);
            addPasswordLauncher.launch(intent);
        });
    }
}

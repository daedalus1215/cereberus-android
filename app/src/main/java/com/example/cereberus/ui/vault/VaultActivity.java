package com.example.cereberus.ui.vault;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.cereberus.R;
import com.example.cereberus.data.model.PasswordEntry;

import java.util.List;

public class VaultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PasswordAdapter adapter;
    private List<PasswordEntry> passwordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerViewPasswords), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);  // top padding = status bar height
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewPasswords);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new PasswordAdapter(List.of(
                new PasswordEntry("Google", "user@gmail.com", "password123", ""),
                new PasswordEntry("Reddit", "redditUser", "password456", ""),
                new PasswordEntry("Bank", "bankUser", "securepass", "")
        )));

        FloatingActionButton fabAddPassword = findViewById(R.id.fabAddPassword);
        fabAddPassword.setOnClickListener(v -> {
            Intent intent = new Intent(VaultActivity.this, AddPasswordActivity.class);
            startActivity(intent);
        });
    }
}

package com.example.cereberus.ui.vault;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import com.example.cereberus.ui.util.InsetsUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.cereberus.R;
import com.example.cereberus.data.model.PasswordEntry;

import java.util.List;

public class VaultActivity extends AppCompatActivity {
    private static final int ADD_PASSWORD_REQUEST = 1;

    private RecyclerView recyclerView;
    private PasswordAdapter adapter;
    private List<PasswordEntry> passwordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        InsetsUtil.applyTopInsets(findViewById(R.id.recyclerViewPasswords));

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
            startActivityForResult(intent, ADD_PASSWORD_REQUEST);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PASSWORD_REQUEST && resultCode == RESULT_OK) {
            String service = data.getStringExtra("service");
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            String notes = data.getStringExtra("notes");

            PasswordEntry newEntry = new PasswordEntry(service, username, password, notes);
            passwordList.add(newEntry);
            adapter.notifyItemInserted(passwordList.size() - 1);
        }
    }

}

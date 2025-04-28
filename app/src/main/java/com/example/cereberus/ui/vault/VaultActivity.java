package com.example.cereberus.ui.vault;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cereberus.R;
import com.example.cereberus.data.model.PasswordEntry;

import java.util.ArrayList;
import java.util.List;

public class VaultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PasswordAdapter adapter;
    private List<PasswordEntry> passwordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        recyclerView = findViewById(R.id.recyclerViewPasswords);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data for now
        adapter = new PasswordAdapter(List.of(
                new PasswordEntry("Google", "user@gmail.com", "password123", ""),
                new PasswordEntry("Reddit", "redditUser", "password456", ""),
                new PasswordEntry("Bank", "bankUser", "securepass", "")
        ));
        recyclerView.setAdapter(adapter);
    }
}

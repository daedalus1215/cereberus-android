package com.example.cereberus.ui.vault;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cereberus.R;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText editTextService;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        editTextService = findViewById(R.id.editTextService);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> {
            // Later we'll save to local storage
            finish(); // For now just go back
        });
    }
}

package com.example.cereberus.ui.vault;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cereberus.R;
import com.example.cereberus.ui.util.InsetsUtil;

public class AddPasswordActivity extends AppCompatActivity {
    private EditText serviceInput, usernameInput, passwordInput, notesInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        InsetsUtil.applyTopInsets(findViewById(R.id.rootView));

        serviceInput = findViewById(R.id.editTextService);
        usernameInput = findViewById(R.id.editTextUsername);
        passwordInput = findViewById(R.id.editTextPassword);
        notesInput = findViewById(R.id.editTextNotes);

        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("service", serviceInput.getText().toString());
            result.putExtra("username", usernameInput.getText().toString());
            result.putExtra("password", passwordInput.getText().toString());
            result.putExtra("notes", notesInput.getText().toString());

            setResult(RESULT_OK, result);
            finish();
        });
    }
}

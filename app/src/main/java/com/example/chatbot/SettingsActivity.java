package com.example.chatbot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private EditText nameEditText;
    private Button saveButton;
    private SharedPreferencesUtil prefsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefsUtil = new SharedPreferencesUtil(this);
        nameEditText = findViewById(R.id.nameEditText);
        saveButton = findViewById(R.id.saveButton);

        // Load current name
        String currentName = prefsUtil.getUserName();
        if (currentName != null) {
            nameEditText.setText(currentName);
        }

        saveButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString().trim();
            if (!newName.isEmpty()) {
                prefsUtil.saveUserName(newName);
                Toast.makeText(this, "Name saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            }
        });
    }
} 
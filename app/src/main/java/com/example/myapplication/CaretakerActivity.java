package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class CaretakerActivity extends AppCompatActivity {

    private EditText nameEdit, phoneEdit;
    private Button nextBtn;
    private static final String CARETAKER_FILE = "caretaker.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker);

        nameEdit = findViewById(R.id.caretakerName);
        phoneEdit = findViewById(R.id.caretakerPhone);
        nextBtn = findViewById(R.id.caretakerNext);

        nextBtn.setOnClickListener(v -> saveAndNext());
    }

    private void saveAndNext() {
        String name = nameEdit.getText().toString().trim();
        String phone = phoneEdit.getText().toString().trim();

        if(name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Caretaker caretaker = new Caretaker(name, phone);

        try(FileOutputStream fos = openFileOutput(CARETAKER_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(caretaker);
        } catch(Exception e) {
            Toast.makeText(this, "Failed to save caretaker info", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, ReminderActivity.class);
        startActivity(intent);
    }
}

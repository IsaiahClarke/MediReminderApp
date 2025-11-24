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

public class MedicationActivity extends AppCompatActivity {

    private EditText medNameEdit, dosageEdit, freqEdit, timerEdit;
    private Button nextBtn;
    private static final String MED_FILE = "medication.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        medNameEdit = findViewById(R.id.medName);
        dosageEdit = findViewById(R.id.medDosage);
        freqEdit = findViewById(R.id.medFrequency);
        timerEdit = findViewById(R.id.medTimer);
        nextBtn = findViewById(R.id.medNext);

        nextBtn.setOnClickListener(v -> saveAndNext());
    }

    private void saveAndNext() {
        String name = medNameEdit.getText().toString().trim();
        String dosage = dosageEdit.getText().toString().trim();
        String frequency = freqEdit.getText().toString().trim();
        String timerStr = timerEdit.getText().toString().trim();

        if(name.isEmpty() || dosage.isEmpty() || frequency.isEmpty() || timerStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int timerSeconds;
        try {
            timerSeconds = Integer.parseInt(timerStr);
        } catch(NumberFormatException e) {
            Toast.makeText(this, "Invalid timer value", Toast.LENGTH_SHORT).show();
            return;
        }

        Medication med = new Medication(name, dosage, frequency, timerSeconds);

        try(FileOutputStream fos = openFileOutput(MED_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(med);
        } catch(Exception e) {
            Toast.makeText(this, "Failed to save medication info", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, CaretakerActivity.class);
        startActivity(intent);
    }
}

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

public class PatientInfoActivity extends AppCompatActivity {

    private EditText nameEdit, ageEdit, conditionEdit;
    private Button nextBtn;
    private static final String PATIENT_FILE = "patient.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        nameEdit = findViewById(R.id.patientName);
        ageEdit = findViewById(R.id.patientAge);
        conditionEdit = findViewById(R.id.patientCondition);
        nextBtn = findViewById(R.id.patientNext);

        nextBtn.setOnClickListener(v -> saveAndNext());
    }

    private void saveAndNext() {
        String name = nameEdit.getText().toString().trim();
        String age = ageEdit.getText().toString().trim();
        String condition = conditionEdit.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(this, "Please enter patient name", Toast.LENGTH_SHORT).show();
            return;
        }

        Patient patient = new Patient(name, age, condition);

        try(FileOutputStream fos = openFileOutput(PATIENT_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(patient);
        } catch(Exception e) {
            Toast.makeText(this, "Failed to save patient info", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, MedicationActivity.class);
        startActivity(intent);
    }
}

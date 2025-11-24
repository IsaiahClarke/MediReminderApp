package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import java.io.ObjectInputStream;

public class ReminderActivity extends AppCompatActivity {

    private TextView summaryText, timerText;
    private Button startBtn, exitBtn;

    private static final String PATIENT_FILE = "patient.dat";
    private static final String MED_FILE = "medication.dat";
    private static final String CARETAKER_FILE = "caretaker.dat";

    private Patient patient;
    private Medication medication;
    private Caretaker caretaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        summaryText = findViewById(R.id.summaryText);
        timerText = findViewById(R.id.timerText);
        startBtn = findViewById(R.id.startTimerBtn);
        exitBtn = findViewById(R.id.exitBtn);

        loadAllData();
        displaySummary();

        startBtn.setOnClickListener(v -> startTimer());
        exitBtn.setOnClickListener(v -> finish());


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    private void loadAllData() {
        try(ObjectInputStream ois = new ObjectInputStream(openFileInput(PATIENT_FILE))) {
            patient = (Patient) ois.readObject();
        } catch(Exception e) {  Toast.makeText(this, "Failed to load patient info", Toast.LENGTH_LONG).show(); }

        try(ObjectInputStream ois = new ObjectInputStream(openFileInput(MED_FILE))) {
            medication = (Medication) ois.readObject();
        } catch(Exception e) {  Toast.makeText(this, "Failed to load patient info", Toast.LENGTH_LONG).show(); }

        try(ObjectInputStream ois = new ObjectInputStream(openFileInput(CARETAKER_FILE))) {
            caretaker = (Caretaker) ois.readObject();
        } catch(Exception e) {  Toast.makeText(this, "Failed to load patient info", Toast.LENGTH_LONG).show(); }
    }

    private void displaySummary() {
        if(patient != null && medication != null && caretaker != null) {
            summaryText.setText("Patient: " + patient.toString() +
                    "\nMedication: " + medication.toString() +
                    "\nCaretaker: " + caretaker.toString());
        }
    }

    private void startTimer() {
        int duration = medication != null ? medication.getTimerSeconds() : 45;
        startBtn.setEnabled(false);

        new CountDownTimer(duration * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time left: " + millisUntilFinished / 1000 + " sec");
            }

            @Override
            public void onFinish() {
                timerText.setText("Time has elapsed.");
                sendSms();
            }
        }.start();
    }

    private void sendSms() {
        if(caretaker == null || medication == null || patient == null) return;

        String message = "Reminder: " + patient.getName() + " must take " +
                medication.getName() + " (" + medication.getDosage() + ").";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(caretaker.getPhone(), null, message, null, null);
            Toast.makeText(this, "SMS sent to " + caretaker.getName(), Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_LONG).show();
        }
    }
}

package com.danielk.smsapplication;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etPhoneNumber, etMessage;
    private Button btnSendSms, btnOpenSmsApp;

    //register a launcher, launch it, handle the callback
    private final ActivityResultLauncher<String> smsPermissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult( Boolean isGranted) {
                            if(isGranted) {
                                sendSms();
                            } else {
                                Toast.makeText(MainActivity.this, "SMS permission denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etMessage = findViewById(R.id.etMessage);
        btnSendSms = findViewById(R.id.btnSendSms);
        btnOpenSmsApp = findViewById(R.id.btnOpenSmsApp);

        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                } else {
                    smsPermissionLauncher.launch(Manifest.permission.SEND_SMS);
                }
            }
        });

        btnOpenSmsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etPhoneNumber.getText().toString().trim();
                String message = etMessage.getText().toString().trim();

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + number));
                intent.putExtra("sms_body", message);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "No messaging app found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Sending method
    @SuppressWarnings("deprecation")
    private void sendSms() {
        String number = etPhoneNumber.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        if (number.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Enter a number and a message", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager smsManager;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                smsManager = getSystemService(SmsManager.class);
            } else {
                smsManager = SmsManager.getDefault();
            }
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(this, "message is sent", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Message is not sent", Toast.LENGTH_SHORT).show();
        }
    }
}
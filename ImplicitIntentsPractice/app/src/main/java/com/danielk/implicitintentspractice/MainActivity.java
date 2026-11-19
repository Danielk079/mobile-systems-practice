package com.danielk.implicitintentspractice;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import android.view.View;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etUrl, etPhone, etMessage;
    Button btnWeb, btnDial, btnShare, btnEmail;

    //helper method
    private void launchSafely(Intent intent) {
        try{
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(this, "No app found to handle this action!", Toast.LENGTH_SHORT).show();
        }

    }

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

        etUrl = findViewById(R.id.etUrl);
        etPhone = findViewById(R.id.etPhone);
        etMessage = findViewById(R.id.etMessage);
        btnWeb = findViewById(R.id.btnWeb);
        btnDial = findViewById(R.id.btnDial);
        btnShare = findViewById(R.id.btnShare);
        btnEmail = findViewById(R.id.btnEmail);

        //Open Website (browser)
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etUrl.getText().toString().trim();
                if(url.isEmpty() ) {
                    Toast.makeText(MainActivity.this, "Enter a website first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                launchSafely(intent);
            }
        });

        //Dial Number (phone app)
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String number = etPhone.getText().toString().trim();
                if(number.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter a phone number first", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number)));
                launchSafely(intent);
            }
        });

        //Share Text (any sharing app)
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String message = etMessage.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                launchSafely(Intent.createChooser(intent, "Share via"));
            }
        });

        //Send Email (email app)
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Hello from my app");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                launchSafely(intent);
            }
        });

    }
}
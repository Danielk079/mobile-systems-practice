package com.danielk.explicitintentspractice1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class GreetingActivity extends AppCompatActivity {

    TextView tvGreeting, tvDetails;
    Button btnBack, btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_greeting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvGreeting = findViewById(R.id.tvGreeting);
        tvDetails = findViewById(R.id.tvDetails);
        btnBack = findViewById(R.id.btnBack);
        btnConfirm = findViewById(R.id.btnConfirm);

        Intent receivedIntent = getIntent();
        String name = receivedIntent.getStringExtra("name");
        int age =  receivedIntent.getIntExtra("age", 0);

        tvGreeting.setText(getString(R.string.greeting_message, name));
        tvDetails.setText(getString(R.string.age_details, age));

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reply", "Welcome " + name + ", your details are confirmed!");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


    }
}
package com.danielk.mpesamenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMoney extends AppCompatActivity {

    EditText editTextNumber;
    Button buttonOk, buttonCancel;

    // TODO: replace this with YOUR actual phone number
    private final String MY_NUMBER = "0794723456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        editTextNumber = findViewById(R.id.editTextTextPersonName);
        buttonOk = findViewById(R.id.button);
        buttonCancel = findViewById(R.id.button3);

        // OK button: check the entered number against your own number
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredNumber = editTextNumber.getText().toString().trim();

                if (enteredNumber.equals(MY_NUMBER)) {
                    Toast.makeText(SendMoney.this, "Money has been sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SendMoney.this, "Please enter the correct number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // CANCEL button: just close this screen and go back to the menu
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
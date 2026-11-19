package com.danielk.explicitintentspractice1;

import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName, etAge;
    Button btnNext;

    private final ActivityResultLauncher<Intent> greetingLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent data = result.getData();
                                if (data != null) {
                                    String reply = data.getStringExtra("reply");
                                    Toast.makeText(MainActivity.this, reply, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "No confirmation received", Toast.LENGTH_SHORT).show();
                            }
                        }

                });

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

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String ageText = etAge.getText().toString().trim();

                if (name.isEmpty() || ageText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in both fields!!", Toast.LENGTH_SHORT ).show();
                    return;
                }

                int age;
                try{
                    age = Integer.parseInt(ageText);
                }catch(NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Please enter a  valid age!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, GreetingActivity.class );
                intent.putExtra("name", name); // key = "name", value = a String
                intent.putExtra("age", age);   // key = "age", value = an int
                greetingLauncher.launch(intent);
            }

        });
    }
}
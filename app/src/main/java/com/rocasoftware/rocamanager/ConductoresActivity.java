package com.rocasoftware.rocamanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

public class ConductoresActivity extends AppCompatActivity {

    Button registroConductorButton;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductores);

        registroConductorButton = findViewById(R.id.registroConductorButton);


        registroConductorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConductoresActivity.this,ConductorRegistroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ConductoresActivity.this,PrincipalActivity.class);
        startActivity(intent);
        finish();
    }
}
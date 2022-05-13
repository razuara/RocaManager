package com.rocasoftware.rocamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PrincipalActivity extends AppCompatActivity {

    TextView nombreCompletoTextView;
    CardView conductoresCardView,vehiculosCardView,viajesCardView,logoutCardView;
    androidx.appcompat.widget.Toolbar myToolbar;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        nombreCompletoTextView = findViewById(R.id.nombreCompletoTextView);
        conductoresCardView = findViewById(R.id.conductoresCardView);
        vehiculosCardView = findViewById(R.id.vehiculosCardView);
        viajesCardView = findViewById(R.id.viajesCardView);
        logoutCardView = findViewById(R.id.logoutCardView);

        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);


        String nombreCompleto = getIntent().getStringExtra("nombreCompleto");
        nombreCompletoTextView.setText(nombreCompleto);

        conductoresCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,ConductoresActivity.class);
                startActivity(intent);
                finish();
            }
        });

        vehiculosCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,VehiculosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viajesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this,ViajesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PrincipalActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
package com.rocasoftware.rocamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    CardView conductoresCardView,logoutCardView;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        nombreCompletoTextView = findViewById(R.id.nombreCompletoTextView);
        conductoresCardView = findViewById(R.id.conductoresCardView);
        logoutCardView = findViewById(R.id.logoutCardView);


        //hace referencia al nodo principal de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child("managers");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String apellido = snapshot.child("apellido").getValue().toString();
                    nombreCompletoTextView.setText(nombre + apellido);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                nombreCompletoTextView.setText("Error");
            }
        });


        conductoresCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,ConductoresActivity.class);
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
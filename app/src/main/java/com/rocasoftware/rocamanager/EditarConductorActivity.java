package com.rocasoftware.rocamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditarConductorActivity extends AppCompatActivity {

    TextView pruebaTextView,nombreTextView;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_conductor);

        pruebaTextView = findViewById(R.id.pruebaTextView);
        nombreTextView = findViewById(R.id.nombreTextView);

        String idConductor = getIntent().getStringExtra("idConductor");

        pruebaTextView.setText(idConductor);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("conductores").child(user.getUid());

        mDatabase.child(idConductor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    nombreTextView.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                nombreTextView.setText("Error");
            }
        });



    }
}
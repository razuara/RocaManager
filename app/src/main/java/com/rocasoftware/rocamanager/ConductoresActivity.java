package com.rocasoftware.rocamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConductoresActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ConductorAdapter conductorAdapter;
    Button registroConductorButton;

    private DatabaseReference mDatabase;

    @Override
    protected void onStart() {
        super.onStart();
        conductorAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        conductorAdapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductores);

        recyclerView = (RecyclerView)findViewById(R.id.conductoresRecyclerView);
        registroConductorButton = findViewById(R.id.registroConductorButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions<ConductorModel> options =
                new FirebaseRecyclerOptions.Builder<ConductorModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("conductores")
                                .child(user.getUid()), ConductorModel.class)
                        .build();
        conductorAdapter = new ConductorAdapter(options);
        recyclerView.setAdapter(conductorAdapter);

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child("managers").child(user.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nombre = snapshot.child("nombre").getValue().toString();
                String apellido = snapshot.child("apellido").getValue().toString();
                String nombreCompleto = nombre +" "+ apellido;

                Intent intent = new Intent(ConductoresActivity.this,PrincipalActivity.class);
                intent.putExtra("nombreCompleto",nombreCompleto);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = new Intent(ConductoresActivity.this,PrincipalActivity.class);
        startActivity(intent);
        finish();
    }
}
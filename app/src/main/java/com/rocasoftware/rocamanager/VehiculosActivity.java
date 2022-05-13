package com.rocasoftware.rocamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VehiculosActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    VehiculoAdapter vehiculoAdapter;
    Button registroVehiculoButton;
    private DatabaseReference mDatabase;


    @Override
    protected void onStart() {
        super.onStart();
        vehiculoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vehiculoAdapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);

        recyclerView = (RecyclerView)findViewById(R.id.vehiculosRecyclerView);
        registroVehiculoButton = findViewById(R.id.registroVehiculoButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions<VehiculoModel> options =
                new FirebaseRecyclerOptions.Builder<VehiculoModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("vehiculos")
                                .child(user.getUid()), VehiculoModel.class)
                        .build();
        vehiculoAdapter = new VehiculoAdapter(options);
        recyclerView.setAdapter(vehiculoAdapter);


        registroVehiculoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VehiculosActivity.this,VehiculoRegistroActivity.class);
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

                Intent intent = new Intent(VehiculosActivity.this,PrincipalActivity.class);
                intent.putExtra("nombreCompleto",nombreCompleto);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
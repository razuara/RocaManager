package com.rocasoftware.rocamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class VehiculoEditarActivity extends AppCompatActivity {

    TextView tipoVehiculoEditText,marcaEditText,modeloEditText,colorEditText,serieEditText,placaEditText;
    Button actualizarButton,borrarButton;
    DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_editar);

        tipoVehiculoEditText = findViewById(R.id.tipoVehiculoEditText);
        marcaEditText = findViewById(R.id.marcaEditText);
        modeloEditText = findViewById(R.id.modeloEditText);
        colorEditText = findViewById(R.id.colorEditText);
        serieEditText = findViewById(R.id.serieEditText);
        placaEditText = findViewById(R.id.placaEditText);
        actualizarButton = findViewById(R.id.actualizarButton);
        borrarButton = findViewById(R.id.borrarButton);

        String idVehiculo = getIntent().getStringExtra("idVehiculo");
        mDatabase = FirebaseDatabase.getInstance().getReference("vehiculos").child(user.getUid());

        mDatabase.child(idVehiculo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String tipo = snapshot.child("tipo").getValue().toString();
                    String marca = snapshot.child("marca").getValue().toString();
                    String modelo = snapshot.child("modelo").getValue().toString();
                    String color = snapshot.child("color").getValue().toString();
                    String serie = snapshot.child("serie").getValue().toString();
                    String placa = snapshot.child("placa").getValue().toString();
                    tipoVehiculoEditText.setText(tipo);
                    marcaEditText.setText(marca);
                    modeloEditText.setText(modelo);
                    colorEditText.setText(color);
                    serieEditText.setText(serie);
                    placaEditText.setText(placa);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VehiculoEditarActivity.this,"Proceso Cancelado",Toast.LENGTH_LONG).show();
            }
        });

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        borrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAlertDialog();


            }
        });
    }

    private void CreateAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Seguro que quieres borrar este vehiculo?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String idVehiculo = getIntent().getStringExtra("idVehiculo");
                mDatabase = FirebaseDatabase.getInstance().getReference("vehiculos").child(user.getUid()).child(idVehiculo);
                mDatabase.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(VehiculoEditarActivity.this,VehiculosActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(VehiculoEditarActivity.this,"Proceso Cancelado",Toast.LENGTH_LONG).show();
            }
        });
        dialog.create();
        dialog.show();

    }

    private void validate() {

        String tipo = tipoVehiculoEditText.getText().toString().trim();
        String marca = marcaEditText.getText().toString().trim();
        String modelo = modeloEditText.getText().toString().trim();
        String color = colorEditText.getText().toString().trim();
        String serie = serieEditText.getText().toString().trim();
        String placa = placaEditText.getText().toString().trim();

        if (tipo.isEmpty())
        {
            tipoVehiculoEditText.setError("No puede estar vacio");
            return;
        }
        if (marca.isEmpty())
        {
            marcaEditText.setError("No puede estar vacio");
            return;
        }
        if (modelo.isEmpty())
        {
            modeloEditText.setError("No puede estar vacio");
            return;
        }
        if (color.isEmpty())
        {
            colorEditText.setError("No puede estar vacio");
            return;
        }
        if (serie.isEmpty())
        {
            serieEditText.setError("No puede estar vacio");
            return;
        }
        if (placa.isEmpty())
        {
            placaEditText.setError("No puede estar vacio");
            return;
        }

            actualizar(tipo,marca,modelo,color,serie,placa);

    }
    private void actualizar(String tipo,String marca,String modelo, String color,String serie,String placa) {

        String idVehiculo = getIntent().getStringExtra("idVehiculo");
        mDatabase = FirebaseDatabase.getInstance().getReference("vehiculos").child(user.getUid()).child(idVehiculo);

        mDatabase.child("tipo").setValue(tipo);
        mDatabase.child("marca").setValue(marca);
        mDatabase.child("modelo").setValue(modelo);
        mDatabase.child("color").setValue(color);
        mDatabase.child("serie").setValue(serie);
        mDatabase.child("placa").setValue(placa);

        Intent intent = new Intent(VehiculoEditarActivity.this,VehiculosActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VehiculoEditarActivity.this,VehiculosActivity.class);
        startActivity(intent);
        finish();
    }
}
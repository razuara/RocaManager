package com.rocasoftware.rocamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class VehiculoRegistroActivity extends AppCompatActivity {

    EditText tipoVehiculoEditText,marcaEditText,modeloEditText,colorEditText,serieEditText,placaEditText;
    Button registrarButton;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_registro);

        tipoVehiculoEditText = findViewById(R.id.tipoVehiculoEditText);
        marcaEditText = findViewById(R.id.marcaEditText);
        modeloEditText = findViewById(R.id.modeloEditText);
        colorEditText = findViewById(R.id.colorEditText);
        serieEditText = findViewById(R.id.serieEditText);
        placaEditText = findViewById(R.id.placaEditText);
        registrarButton = findViewById(R.id.registrarButton);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
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
            tipoVehiculoEditText.setError("Tipo de Vehiculo esta vacio");
            return;
        }
        if (marca.isEmpty())
        {
            marcaEditText.setError("Marca de Vehiculo esta vacia");
            return;
        }
        if (modelo.isEmpty())
        {
            modeloEditText.setError("Modelo de Vehiculo esta vacio");
            return;
        }
        if (color.isEmpty())
        {
            colorEditText.setError("Color de Vehiculo esta vacio");
            return;
        }
        if (serie.isEmpty())
        {
            serieEditText.setError("# de serie de Vehiculo esta vacio");
            return;
        }
        if (placa.isEmpty())
        {
            placaEditText.setError("Placa de Vehiculo esta vacia");
            return;
        }
        else
        {
            registrar(tipo,marca,modelo,color,serie,placa);
        }
    }

    private void registrar(String tipo, String marca, String modelo, String color,String serie, String placa) {
        String fechaRegistro = getTimeDate();

        Map<String,Object> map = new HashMap<>();
        map.put("fechaRegistro",fechaRegistro);
        map.put("tipo",tipo);
        map.put("marca",marca);
        map.put("modelo",modelo);
        map.put("color",color);
        map.put("serie",serie);
        map.put("placa",placa);
        map.put("vehiculoImgSrc","");

        String UID = mAuth.getUid().toString();
        mDatabase.child("vehiculos")
                .child(UID)
                .push()
                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(VehiculoRegistroActivity.this,VehiculosActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(VehiculoRegistroActivity.this, "Fallo en registrarse", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public static String getTimeDate() { // without parameter argument
        try{
            Date netDate = new Date(); // current time from here
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            return sfd.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VehiculoRegistroActivity.this,VehiculosActivity.class);
        startActivity(intent);
        finish();
    }
}
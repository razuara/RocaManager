package com.rocasoftware.rocamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class EditarConductorActivity extends AppCompatActivity {

    TextView nombreEditText,apellidoEditText,telefonoEditText,emailEditText,passwordEditText,repetirPasswordEditText;
    Button actualizarButton;
    DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_conductor);

        nombreEditText = findViewById(R.id.nombreEditText);
        apellidoEditText = findViewById(R.id.apellidoEditText);
        telefonoEditText = findViewById(R.id.telefonoEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repetirPasswordEditText = findViewById(R.id.repetirPasswordEditText);
        actualizarButton = findViewById(R.id.actualizarButton);



        emailEditText.setFocusable(false);
        emailEditText.setClickable(false);

        String idConductor = getIntent().getStringExtra("idConductor");


        mDatabase = FirebaseDatabase.getInstance().getReference("conductores").child(user.getUid());

        mDatabase.child(idConductor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String apellido = snapshot.child("apellido").getValue().toString();
                    String telefono = snapshot.child("telefono").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String password = snapshot.child("password").getValue().toString();
                    String repetirPassword = snapshot.child("password").getValue().toString();
                    nombreEditText.setText(nombre);
                    apellidoEditText.setText(apellido);
                    telefonoEditText.setText(telefono);
                    emailEditText.setText(email);
                    passwordEditText.setText(password);
                    repetirPasswordEditText.setText(repetirPassword);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                nombreEditText.setText("Error");
            }
        });

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


    }

    private void validate() {

        String nombre = nombreEditText.getText().toString().trim();
        String apellido = apellidoEditText.getText().toString().trim();
        String telefono = telefonoEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repetirPassword = repetirPasswordEditText.getText().toString().trim();


        if (password.isEmpty() || password.length() < 8)
        {
            passwordEditText.setError("Se necesitan mas de 8 caracteres");
            return;
        }
        else if(!Pattern.compile("[0-9]").matcher(password).find())
        {
            passwordEditText.setError("Al menos un numero");
            return;
        }
        else
        {
            passwordEditText.setError(null);
        }
        if (!repetirPassword.equals(password))
        {
            repetirPasswordEditText.setError("Deben ser iguales");
            return;
        }
        else
        {
            actualizar(nombre,apellido,telefono,password);
        }
    }
    private void actualizar(String nombre,String apellido,String telefono, String password) {

        String idConductor = getIntent().getStringExtra("idConductor");
        mDatabase = FirebaseDatabase.getInstance().getReference("conductores").child(user.getUid()).child(idConductor);

        mDatabase.child("nombre").setValue(nombre);
        mDatabase.child("apellido").setValue(apellido);
        mDatabase.child("telefono").setValue(telefono);
        mDatabase.child("password").setValue(password);
        mDatabase.child("nombreCompleto").setValue(nombre + " " + apellido);

        Intent intent = new Intent(EditarConductorActivity.this,ConductoresActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditarConductorActivity.this,ConductoresActivity.class);
        startActivity(intent);
        finish();
    }

}
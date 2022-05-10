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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {
    EditText nombreEditText,apellidoEditText,telefonoEditText,emailEditText,passwordEditText,repetirpasswordEditText;
    Button registrarButton;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombreEditText = findViewById(R.id.nombreEditText);
        apellidoEditText = findViewById(R.id.apellidoEditText);
        telefonoEditText = findViewById(R.id.telefonoEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repetirpasswordEditText = findViewById(R.id.repetirpasswordEditText);
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
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repetirPassword = repetirpasswordEditText.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError("Correo Invalido");
            return;
        }
        else
        {
            emailEditText.setError(null);
        }
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

            repetirpasswordEditText.setError("Deben ser iguales");
            return;
        }
        else
        {
            registrar(email,password);
        }
    }

    private void registrar(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            String nombre = nombreEditText.getText().toString();
                            String apellido = apellidoEditText.getText().toString();
                            String telefono = telefonoEditText.getText().toString();
                            String email = emailEditText.getText().toString();
                            String fechaRegistro = getTimeDate();
                            String fechaUltimoLogin = getTimeDate();
                            String tipo = "Manager";

                            Map<String,Object> map = new HashMap<>();
                            map.put("nombre",nombre);
                            map.put("apellido",apellido);
                            map.put("telefono",telefono);
                            map.put("email",email);
                            map.put("fechaRegistro",fechaRegistro);
                            map.put("fechaUltimoLogin",fechaUltimoLogin);
                            map.put("tipo",tipo);

                            String UID = mAuth.getUid().toString();
                            mDatabase.child("usuario").child(UID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if (task2.isSuccessful())
                                    {
                                        Intent intent = new Intent(RegistroActivity.this,PrincipalActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(RegistroActivity.this, "Fallo en registrarse", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(RegistroActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
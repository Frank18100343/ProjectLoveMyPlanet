package dev.frankperez.projectlovemyplanet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextView mButtonVoluntario;
    private TextView mButtonAuspiciador;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonAcceder;
    FirebaseAuth mAuth;
    DatabaseReference db;

    private String email = "";
    private String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        mEditTextEmail = findViewById(R.id.mEditTextEmail);
        mEditTextPassword = findViewById(R.id.mEditTextPassword);
        mButtonVoluntario = findViewById(R.id.mButtonVoluntario);
        mButtonAuspiciador = findViewById(R.id.mButtonAuspiciador);
        mButtonAcceder = findViewById(R.id.mButtonAcceder);


        mButtonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    buscarRolUsuario();
                                    Toast.makeText(LoginActivity.this, "Autentificación Correcta", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }

        });


        mButtonVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RegistrarVoluntarioActivity.class));
            }
        });

        mButtonAuspiciador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RegistrarAuspiciadorActivity.class));
            }
        });


    }

    public void buscarRolUsuario() {
        String id = mAuth.getCurrentUser().getUid();
        db.child("voluntarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //Redireccionar a su layaout Voluntario
                    startActivity(new Intent(LoginActivity.this, VoluntarioActivity.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.child("auspiciadores").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                if (snapshot2.exists()) {
                    //Redireccionar a su layaout Auspiciador
                    startActivity(new Intent(LoginActivity.this, AuspiciadorActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
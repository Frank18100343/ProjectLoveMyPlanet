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

public class LoginActivity extends AppCompatActivity {

    private TextView mButtonVoluntario;
    private TextView mButtonAuspiciador;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonAcceder;
    FirebaseAuth mAuth;


    private String email = "";
    private String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
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
}
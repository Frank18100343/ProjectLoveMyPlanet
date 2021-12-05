package dev.frankperez.projectlovemyplanet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

import dev.frankperez.projectlovemyplanet.ui.fragments.Actividad;

public class LoginActivity extends AppCompatActivity {

    TextView mTextViewData;
    Spinner mSpinnerActividades;


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

        //mEditTextEmail.setText("julian@esan.com");
        mEditTextPassword = findViewById(R.id.mEditTextPassword);
        //mEditTextPassword.setText("123456");
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
                                    //buscarRolUsuario();
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

        boolean isAuspiciador = ((RadioButton) findViewById(R.id.rbAuspiciador)).isChecked();
        GlobalClass.Tipo = (isAuspiciador ? "Auspiciador" : "Voluntario");

        String id = mAuth.getCurrentUser().getUid();
        String tipo =(isAuspiciador ? "auspiciadores" : "voluntarios");
        db.child(tipo).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                if (ds.exists()) {
                    GlobalClass.idUsuario = ds.getKey();
                    GlobalClass.email = ds.child("email").getValue().toString();
                    GlobalClass.telefono = ds.child("telefono").getValue().toString();
                    if (!isAuspiciador) {
                        GlobalClass.apellidos = ds.child("apellidos").getValue().toString();
                        GlobalClass.nombres = ds.child("nombres").getValue().toString();
                        GlobalClass.saldoPuntos = Integer.parseInt(ds.child("saldopuntos").getValue().toString());
                    } else {
                        GlobalClass.ruc = ds.child("ruc").getValue().toString();
                        GlobalClass.razonSocial = ds.child("razonsocial").getValue().toString();
                        GlobalClass.nombres = GlobalClass.razonSocial;
                        GlobalClass.apellidos = "";
                    }
                    startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "No se encontró al responsable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}

package dev.frankperez.projectlovemyplanet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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


    public String nombres;
    public String ruc;
    public String razonSocial;
    public String apellidos;
    public String correo;
    public String telefono;
    public String saldoPuntos;


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

                    GlobalClass globalClass = (GlobalClass) getApplicationContext();

                    nombres  = snapshot.child("nombres").getValue().toString();
                    apellidos  = snapshot.child("apellidos").getValue().toString();
                    correo  = snapshot.child("email").getValue().toString();
                    telefono  = snapshot.child("telefono").getValue().toString();
                    saldoPuntos  = snapshot.child("saldoPuntos").getValue().toString();

                    globalClass.setNombres(nombres);
                    globalClass.setApellidos(apellidos);
                    globalClass.setEmail(correo);
                    globalClass.setTelefono(telefono);
                    globalClass.setSaldoPuntos(saldoPuntos);

                    //Redireccionar a su layaout Voluntario
                    startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                    //startActivity(new Intent(LoginActivity.this, VoluntarioActivity.class));
                    //falta ocultar opciones segun perfil
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
/*
        public void cargarActividades() {
            final List<Actividad> actividades = new ArrayList<>();
            db.child("actividades").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String id = ds.getKey();
                            String nombre = ds.child("nombre").getValue().toString();
                            actividades.add(new Actividad(id, nombre));
                        }
                        ArrayAdapter<Actividad> arrayAdapter = new ArrayAdapter<>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, actividades);
                        mSpinnerActividades.setAdapter(arrayAdapter);
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
                        startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                        //falta ocultar opciones segun perfil
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }*/
    }


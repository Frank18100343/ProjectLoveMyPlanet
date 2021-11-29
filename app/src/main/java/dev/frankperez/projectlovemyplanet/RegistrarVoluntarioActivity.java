package dev.frankperez.projectlovemyplanet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarVoluntarioActivity extends AppCompatActivity {

    private EditText mEditTextNombres_V;
    private EditText mEditTextApellidos_V;
    private EditText mEditTextTelefono_V;
    private EditText mEditTextEmail_V;
    private EditText mEditTextPassword_V;
    private Button mButtonRegVol;

    //Variables de entrada
    private String nombres = "";
    private String apellidos = "";
    private String telefono = "";
    private String email = "";
    private String password = "";
    FirebaseAuth mAuth;
    DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_voluntario);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();


        mEditTextNombres_V = findViewById(R.id.mEditTextNombres_V);
        mEditTextApellidos_V = findViewById(R.id.mEditTextApellidos_V);
        mEditTextTelefono_V = findViewById(R.id.mEditTextTelefono_V);
        mEditTextEmail_V = findViewById(R.id.mEditTextEmail_V);
        mEditTextPassword_V = findViewById(R.id.mEditTextPassword_V);
        mButtonRegVol = findViewById(R.id.mButtonRegVol);

        mButtonRegVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombres = mEditTextNombres_V.getText().toString();
                apellidos = mEditTextApellidos_V.getText().toString();
                telefono = mEditTextTelefono_V.getText().toString();
                email = mEditTextEmail_V.getText().toString();
                password = mEditTextPassword_V.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {

                    if (password.length() >= 6) {
                            registrarUsuario();
                    } else {
                        Toast.makeText(RegistrarVoluntarioActivity.this, "El password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegistrarVoluntarioActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private void registrarUsuario(){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String id = mAuth.getCurrentUser().getUid();

                    Map<String,Object> map = new HashMap<>();
                    map.put("nombres",nombres);
                    map.put("apellidos",apellidos);
                    map.put("telefono",telefono);
                    map.put("email",email);
                    map.put("saldoPuntos",0);
                    //map.put("password",System.text.Encoding.UTF8.GetBytes(password));

                    db.child("voluntarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegistrarVoluntarioActivity.this, RegistrarVoluntarioActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistrarVoluntarioActivity.this, "No se pudieron registrar los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else{
                    Toast.makeText(RegistrarVoluntarioActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
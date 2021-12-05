package dev.frankperez.projectlovemyplanet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarAuspiciadorActivity extends AppCompatActivity {

    private EditText mEditTextRazonSocial_A;
    private EditText mEditTextRuc_A;
    private EditText mEditTextTelefono_A;
    private EditText mEditTextEmail_A;
    private EditText mEditTextPassword_A;
    private Button mButtonRegAus;
private CheckBox checkAceptar;
    //Variables de entrada
    private String razonsocial = "";
    private String ruc = "";
    private String telefono = "";
    private String email = "";
    private String password = "";
    FirebaseAuth mAuth;
    DatabaseReference db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_auspiciador);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        mEditTextRazonSocial_A = findViewById(R.id.mEditTextRazonSocial_A);
        mEditTextRuc_A = findViewById(R.id.mEditTextRuc_A);
        mEditTextTelefono_A = findViewById(R.id.mEditTextTelefono_A);
        mEditTextEmail_A = findViewById(R.id.mEditTextEmail_A);
        mEditTextPassword_A = findViewById(R.id.mEditTextPassword_A);
        mButtonRegAus = findViewById(R.id.mButtonRegAus);
        checkAceptar = findViewById(R.id.checkAceptar);

        checkAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonRegAus.setEnabled(checkAceptar.isChecked());
            }
        });

        mButtonRegAus.setEnabled(false);
        mButtonRegAus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAceptar.isChecked()){
                    Toast.makeText(RegistrarAuspiciadorActivity.this, "Debe aceptar los terminos y condiciones", Toast.LENGTH_SHORT).show();
                    return;
                }
                razonsocial = mEditTextRazonSocial_A.getText().toString();
                ruc = mEditTextRuc_A.getText().toString();
                telefono = mEditTextTelefono_A.getText().toString();
                email = mEditTextEmail_A.getText().toString();
                password = mEditTextPassword_A.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {

                    if (password.length() >= 6) {
                        registrarUsuario();
                    } else {
                        Toast.makeText(RegistrarAuspiciadorActivity.this, "El password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegistrarAuspiciadorActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
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
                    map.put("razonsocial",razonsocial);
                    map.put("ruc",ruc);
                    map.put("telefono",telefono);
                    map.put("email",email);
                    map.put("saldoPuntos",0);
                    //map.put("password",System.text.Encoding.UTF8.GetBytes(password));

                    db.child("auspiciadores").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegistrarAuspiciadorActivity.this, RegistrarAuspiciadorActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistrarAuspiciadorActivity.this, "No se pudieron registrar los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else{
                    Toast.makeText(RegistrarAuspiciadorActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
}
}
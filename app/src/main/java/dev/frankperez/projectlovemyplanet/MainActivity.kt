package dev.frankperez.projectlovemyplanet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mButtonVoluntario : TextView =  findViewById(R.id.mButtonVoluntario);
        val mButtonAuspiciador : TextView =  findViewById(R.id.mButtonAuspiciador);

        mButtonVoluntario.setOnClickListener(){
            val intent  = Intent(this,RegVoluntarioActivity::class.java)
            startActivity(intent);
        }

        mButtonAuspiciador.setOnClickListener(){
            val intent  = Intent(this,RegAuspiciadorActivity::class.java)
            startActivity(intent);
        }

    }
}
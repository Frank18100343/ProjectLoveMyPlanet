package dev.frankperez.projectlovemyplanet.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import dev.frankperez.projectlovemyplanet.GlobalClass
import dev.frankperez.projectlovemyplanet.R
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap
import javax.annotation.Nonnull


class RegistroActividadesFragment : Fragment() {
    var mAuth: FirebaseAuth? = null
    var db: DatabaseReference? = null
    var fire: FirebaseFirestore? = null
    var txtInicio: EditText? =null;
    var txtFin: EditText? =null;
    var spinner: Spinner? = null;
    var txtDescripcioin: EditText? =null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_registro_actividades, container, false)
        txtInicio = view.findViewById<EditText>(R.id.txtinicio)
        txtFin = view.findViewById<EditText>(R.id.txtfin)
        txtDescripcioin = view.findViewById<EditText>(R.id.txtDescripcion)
        try {
            txtInicio!!.setText(GlobalClass.getFecha())
            txtFin!!.setText(GlobalClass.getFecha())
            GlobalClass.showFecha(activity, txtInicio, "Fecha Inicio")
            GlobalClass.showFecha(activity, txtFin, "Fecha Final")

            spinner = view.findViewById<Spinner>(R.id.spinnerActividades)
            mAuth = FirebaseAuth.getInstance()
            db = FirebaseDatabase.getInstance().reference
            fire= FirebaseFirestore.getInstance()

            (view.findViewById<View>(R.id.btnguardar) as Button).setOnClickListener {
                guardaar()
            }
            cargarActividades()

        } catch (ex: Exception) {
            Toast.makeText(activity, ex.message, Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
        return view
    }
    fun guardaar(){
       //fire.collection("ActividadesRegistrados").document("3").set(keys)
    try{
            val vls = HashMap<String, String>()
            vls["Actividad"] = spinner!!.selectedItem.toString()
            vls["Descripcion"] = txtDescripcioin!!.text.toString()
            vls["Inicio"] = txtInicio!!.text.toString()
            vls["Fin"] = txtFin!!.text.toString()
            db!!.child("ActividadesRegistrados").child("1").setValue(vls)
            Toast.makeText(activity, "Guardaro", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(activity, ex.message, Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
    }

    fun cargarActividades() {
        val actividades: MutableList<Actividad> = ArrayList()
        db!!.child("actividades").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(@Nonnull dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        val id = ds.key
                        val nombre = ds.child("nombre").value.toString()
                        actividades.add(Actividad(id, nombre))
                    }
                    val spinnerArrayAdapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, actividades)
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner!!.adapter = spinnerArrayAdapter
                }
            }
            override fun onCancelled(@Nonnull error: DatabaseError) {}
        })
    }

}
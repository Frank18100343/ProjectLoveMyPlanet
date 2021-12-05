package dev.frankperez.projectlovemyplanet.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import dev.frankperez.projectlovemyplanet.GlobalClass
import dev.frankperez.projectlovemyplanet.R
import kotlinx.android.synthetic.main.fragment_registro_actividades.*
import java.lang.Exception
import java.util.*
import javax.annotation.Nonnull


class RegistroActividadesFragment : Fragment() {
    var mAuth: FirebaseAuth? = null
    var db: DatabaseReference? = null
    var fire: FirebaseFirestore? = null
    var txtInicio: TextInputEditText? =null;
    var txtFin: TextInputEditText? =null;
    var spinner: Spinner? = null;
    var txtDescripcioin: TextInputEditText? =null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_registro_actividades, container, false)
        try {
            txtInicio = view.findViewById<TextInputEditText>(R.id.txtInicio)
            txtFin = view.findViewById<TextInputEditText>(R.id.txtFin)
            txtDescripcioin = view.findViewById<TextInputEditText>(R.id.txtDescripcion)
            view.findViewById<TextInputEditText>(R.id.txtUsuario).setText(GlobalClass.email)
            txtInicio!!.setText(GlobalClass.getFecha())
            txtFin!!.setText(GlobalClass.getFecha())
            GlobalClass.showFecha(activity, txtInicio, "Fecha Inicio")
            GlobalClass.showFecha(activity, txtFin, "Fecha Final")

            spinner = view.findViewById<Spinner>(R.id.spinnerActividades)
            mAuth = FirebaseAuth.getInstance()
            db = FirebaseDatabase.getInstance().reference
            fire= FirebaseFirestore.getInstance()
            (view.findViewById<View>(R.id.btnguardar) as Button).setOnClickListener {
                if(GlobalClass.Tipo.equals("Auspiciador")) guardar()
                else
                    Toast.makeText(activity, "Solo se permite crear actividades al Auspiciador", Toast.LENGTH_SHORT).show()
            }
            cargarActividades()

        } catch (ex: Exception) {
            Toast.makeText(activity, ex.message, Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
        return view
    }
    fun guardar(){
    try{
            val vls = HashMap<String, String>()
            vls["Actividad"] = spinner!!.selectedItem.toString()
            vls["Descripcion"] = txtDescripcioin!!.text.toString()
            vls["Direccion"] = txtDireccion!!.text.toString()
            vls["Inicio"] = txtInicio!!.text.toString()
            vls["Fin"] = txtFin!!.text.toString()
            vls["Usuario"] = txtUsuario!!.text.toString()
            vls["Auspiciador"] = GlobalClass.razonSocial
            vls["Puntos"] = txtPuntos!!.text.toString().toInt().toString()

            val mid = Calendar.getInstance().time.time.toString()


            db!!.child("ActividadesRegistrados").child(mid).setValue(vls)
            Toast.makeText(activity, "Guardado", Toast.LENGTH_SHORT).show()

            txtDescripcioin!!.setText("")
        txtDireccion!!.setText("")
        txtInicio!!.setText(GlobalClass.getFecha())
        txtFin!!.setText(GlobalClass.getFecha())
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
                        Log.d("AAAAAAAAAAAAAAA", id+ "  -  "+nombre)
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
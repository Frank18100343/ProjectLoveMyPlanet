package dev.frankperez.projectlovemyplanet.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import dev.frankperez.projectlovemyplanet.GlobalClass
import dev.frankperez.projectlovemyplanet.PrincipalActivity
import dev.frankperez.projectlovemyplanet.R
import dev.frankperez.projectlovemyplanet.ui.fragments.adapter.ActividadAdapter
import dev.frankperez.projectlovemyplanet.ui.fragments.adapter.CanjearAdapter
import dev.frankperez.projectlovemyplanet.ui.fragments.model.ActividadModel
import dev.frankperez.projectlovemyplanet.ui.fragments.model.CanjearModel
import java.lang.Exception
import java.util.*
import javax.annotation.Nonnull
import kotlin.collections.ArrayList

class ListadoActividadesFragment : Fragment() {
    var mAuth: FirebaseAuth? = null
    var db: DatabaseReference? = null
    var rvProducts: RecyclerView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.fragment_listado_actividades, container, false)
         db = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
         rvProducts = view.findViewById(R.id.rvProducts)
        listar()
        return view
    }
fun listar(){
    val lstProducts: ArrayList<ActividadModel> = ArrayList()
    db!!.child("ActividadesRegistrados").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(@Nonnull dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                for (ds in dataSnapshot.children) {
                    val id = ds.key
                    lstProducts.add(
                        ActividadModel(
                            id.toString(),
                            ds.child("Actividad").value.toString(),
                            ds.child("Descripcion").value.toString(),
                            ds.child("Direccion").value.toString(),
                            ds.child("Inicio").value.toString(),
                            ds.child("Fin").value.toString(),
                            ds.child("Puntos").value.toString().toInt()
                        )
                    )
                }
                var adapter: ActividadAdapter=ActividadAdapter(lstProducts)
                rvProducts!!.adapter = adapter
                rvProducts!!.layoutManager = LinearLayoutManager(requireContext())
                adapter.onItemClick = { canje ->
                    itemClick(canje)
                }
            }
        }
        override fun onCancelled(@Nonnull error: DatabaseError) {}
    })
}
    fun itemClick(act: ActividadModel){

        val builder = android.app.AlertDialog.Builder(activity)
        builder.setNegativeButton("No", null)
        builder.setPositiveButton(
            "SI"
        ) { dialogInterface, i ->

            try{
                val vls = HashMap<String, String>()
                val id: String = mAuth!!.getCurrentUser()!!.getUid()
                vls["idUsuario"] = id
                vls["email"] = GlobalClass.email
                vls["puntos"] = act.puntos.toString()
                vls["idActividad"] = act.id
                val mid = Calendar.getInstance().time.time.toString()
                db!!.child("usuarioActividad").child(mid).setValue(vls)



                vls.clear()
                vls["apellidos"] = GlobalClass.apellidos
                vls["email"] = GlobalClass.email
                vls["nombres"] = GlobalClass.nombres
                vls["saldopuntos"] = (GlobalClass.saldoPuntos + act.puntos).toString()
                vls["telefono"] = GlobalClass.telefono

                db!!.child("voluntarios").child(GlobalClass.idUsuario).setValue(vls)

                Toast.makeText(activity, "Unido!!", Toast.LENGTH_SHORT).show()

                GlobalClass.saldoPuntos = GlobalClass.saldoPuntos +  act.puntos
                var main: PrincipalActivity = activity as PrincipalActivity
                main.mostrarTitulos()
                listar()
            } catch (ex: Exception) {
                Toast.makeText(activity, ex.message, Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
        builder.setMessage("¿Está seguro de unirse a esta actividad?")
        builder.create().show()
    }
}
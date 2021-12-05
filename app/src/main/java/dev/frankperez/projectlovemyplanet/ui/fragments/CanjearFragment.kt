package dev.frankperez.projectlovemyplanet.ui.fragments

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.ktx.Firebase
import dev.frankperez.projectlovemyplanet.GlobalClass
import dev.frankperez.projectlovemyplanet.PrincipalActivity
import dev.frankperez.projectlovemyplanet.R
import dev.frankperez.projectlovemyplanet.ui.fragments.adapter.ActividadAdapter
import dev.frankperez.projectlovemyplanet.ui.fragments.model.CanjearModel
import dev.frankperez.projectlovemyplanet.ui.fragments.adapter.CanjearAdapter
import dev.frankperez.projectlovemyplanet.ui.fragments.model.ActividadModel
import kotlinx.android.synthetic.main.fragment_registro_actividades.*
import java.lang.Exception
import java.util.*
import javax.annotation.Nonnull
import kotlin.collections.ArrayList

class CanjearFragment : Fragment() {

    var mAuth: FirebaseAuth? = null
    var db: DatabaseReference? = null
    var rvProducts: RecyclerView?=null
    var  activi:Activity?= null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_canjear, container, false)

        db = FirebaseDatabase.getInstance().reference
        rvProducts = view.findViewById(R.id.rvProducts)
        mAuth = FirebaseAuth.getInstance()
        activi = activity;
        listar()

        return view
    }
    fun listar(){
        val lstProducts: ArrayList<CanjearModel> = ArrayList()
        db!!.child("Productos").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(@Nonnull dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        val id = ds.key
                        lstProducts.add(
                            CanjearModel(
                                id.toString(),
                                ds.child("codProd").value.toString(),
                                ds.child("nombProd").value.toString(),
                                ds.child("stockProd").value.toString().toInt(),
                                ds.child("puntosProd").value.toString().toInt(),
                                ds.child("imgProd").value.toString()
                            )
                        )
                    }
                    var adapter:CanjearAdapter= CanjearAdapter(lstProducts)
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

    fun itemClick(canje: CanjearModel){
        if(canje.stockProducto < 1){
            Toast.makeText(activity, "stock negativo", Toast.LENGTH_SHORT).show()
            return;
        }
        if(canje.puntoProducto > GlobalClass.saldoPuntos){
            Toast.makeText(activity, "No tiene puntos", Toast.LENGTH_SHORT).show()
            return;
        }
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
                vls["puntos"] = canje.puntoProducto.toString()
                vls["idProducto"] = canje.codProducto
                val mid = Calendar.getInstance().time.time.toString()
                db!!.child("usuarioProductoStock").child(mid).setValue(vls)

                vls.clear()
                vls["codProd"] = canje.codProducto
                vls["nombProd"] = canje.descProducto
                vls["stockProd"] =(canje.stockProducto - 1).toString()
                vls["puntosProd"] = canje.puntoProducto.toString()
                vls["imgProd"] = canje.imgProducto
                db!!.child("Productos").child(canje.id).setValue(vls)

                vls.clear()
                vls["apellidos"] = GlobalClass.apellidos
                vls["email"] = GlobalClass.email
                vls["nombres"] = GlobalClass.nombres
                vls["saldopuntos"] = (GlobalClass.saldoPuntos - canje.puntoProducto).toString()
                vls["telefono"] = GlobalClass.telefono

                db!!.child("voluntarios").child(GlobalClass.idUsuario).setValue(vls)

                Toast.makeText(activity, "Canjeado", Toast.LENGTH_SHORT).show()

                GlobalClass.saldoPuntos =GlobalClass.saldoPuntos -  canje.puntoProducto.toString().toInt()
                var main: PrincipalActivity = activity as PrincipalActivity

                main.mostrarTitulos()
                listar()
            } catch (ex: Exception) {
                Toast.makeText(activity, ex.message, Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
        builder.setMessage("¿Está seguro de canjear este producto?")
        builder.create().show()
    }

}
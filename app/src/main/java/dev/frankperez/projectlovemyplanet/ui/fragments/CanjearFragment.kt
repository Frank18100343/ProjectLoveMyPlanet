package dev.frankperez.projectlovemyplanet.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.ktx.Firebase
import dev.frankperez.projectlovemyplanet.R
import dev.frankperez.projectlovemyplanet.ui.fragments.model.CanjearModel
import dev.frankperez.projectlovemyplanet.ui.fragments.adapter.CanjearAdapter

class CanjearFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_canjear, container, false)
        val db = FirebaseFirestore.getInstance()

        val lstProducts: ArrayList<CanjearModel> = ArrayList()
        val rvProducts: RecyclerView = view.findViewById(R.id.rvProducts)

        db.collection("products")
            .addSnapshotListener{snapshots,e->
                if(e!=null){
                    Log.e("Firebase Error","Error al listar los productos")
                    return@addSnapshotListener
                }

                for (dc in snapshots!!.documentChanges){
                    when(dc.type){
                        DocumentChange.Type.ADDED,
                        DocumentChange.Type.MODIFIED,
                        DocumentChange.Type.REMOVED -> {
                            lstProducts.add(
                                CanjearModel(dc.document.data["codProd"].toString(),
                                    dc.document.data["nombProd"].toString(),
                                    dc.document.data["stockProd"].toString(),
                                    dc.document.data["puntosProd"].toString(),
                                    dc.document.data["imgProd"].toString(),
                                )
                            )
                        }
                    }
                }
                rvProducts.adapter = CanjearAdapter(lstProducts)
                rvProducts.layoutManager = LinearLayoutManager(requireContext())
            }

        return view
    }
}
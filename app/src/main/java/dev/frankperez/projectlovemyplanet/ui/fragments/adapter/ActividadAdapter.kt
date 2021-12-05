package dev.frankperez.projectlovemyplanet.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.frankperez.projectlovemyplanet.R
import dev.frankperez.projectlovemyplanet.ui.fragments.model.ActividadModel
import dev.frankperez.projectlovemyplanet.ui.fragments.model.CanjearModel

class ActividadAdapter (private var lstActivs: List<ActividadModel>)
    : RecyclerView.Adapter<ActividadAdapter.ViewHolder>(){

    var onItemClick: ((ActividadModel) -> Unit)? = null

    inner class ViewHolder(itemsView: View): RecyclerView.ViewHolder(itemsView){
        val lblActividad: TextView = itemsView.findViewById(R.id.lblActividad)
        val lblDescripcion: TextView = itemsView.findViewById(R.id.lblDescripcion)
        val lblDireccion: TextView = itemsView.findViewById(R.id.lblDireccion)
        val lblDesde: TextView = itemsView.findViewById(R.id.lblDesde)
        val lblHasta: TextView = itemsView.findViewById(R.id.lblHasta)
        val img: ImageView = itemsView.findViewById(R.id.imgActividad)

        val btnUnirse: Button = itemsView.findViewById(R.id.btnUnirme)
        init {
            btnUnirse.setOnClickListener {
                onItemClick?.invoke(lstActivs[adapterPosition])
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_actividad,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemProduct = lstActivs[position]
        holder.lblActividad.text = itemProduct.actividad
        holder.lblDescripcion.text = itemProduct.descripcion
        holder.lblDireccion.text = itemProduct.direccion
        holder.lblDesde.text = itemProduct.desde
        holder.lblHasta.text = itemProduct.hasta
        Glide.with(holder.itemView.context)
            .load("https://image.freepik.com/vector-gratis/concepto-actividad-limpieza-ninos_284572-57.jpg")
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return lstActivs.size
    }
}
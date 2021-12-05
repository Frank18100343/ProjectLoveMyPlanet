package dev.frankperez.projectlovemyplanet.ui.fragments.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import dev.frankperez.projectlovemyplanet.R
import dev.frankperez.projectlovemyplanet.ui.fragments.model.CanjearModel

class CanjearAdapter (private var lstProducts: List<CanjearModel>)
    : RecyclerView.Adapter<CanjearAdapter.ViewHolder>(){

    var onItemClick: ((CanjearModel) -> Unit)? = null

    inner  class ViewHolder(itemsView: View): RecyclerView.ViewHolder(itemsView){
        val tv_code_Product: TextView = itemsView.findViewById(R.id.txt_cod_product)
        val tv_description_Product: TextView = itemsView.findViewById(R.id.txt_desc_product)
        val tv_stock_Product: TextView = itemsView.findViewById(R.id.txt_stock_product)
        val tv_puntos_Product: TextView = itemsView.findViewById(R.id.txt_puntos_product)
        val iv_producto: ImageView = itemsView.findViewById(R.id.img_products)

        val btnCanjear: Button = itemsView.findViewById(R.id.btn_canjear)


        init {
            btnCanjear.setOnClickListener {
                onItemClick?.invoke(lstProducts[adapterPosition])
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var  view: ViewHolder = ViewHolder(layoutInflater.inflate(R.layout.item_products,parent,false))
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var itemProduct: CanjearModel = lstProducts[position]
        holder.tv_code_Product.text = itemProduct.codProducto
        holder.tv_description_Product.text = itemProduct.descProducto
        holder.tv_stock_Product.text = itemProduct.stockProducto.toString()
        holder.tv_puntos_Product.text = itemProduct.puntoProducto.toString()
        Glide.with(holder.itemView.context)
            .load(itemProduct.imgProducto)
            .into(holder.iv_producto)

    }

    override fun getItemCount(): Int {
        return lstProducts.size
    }
}
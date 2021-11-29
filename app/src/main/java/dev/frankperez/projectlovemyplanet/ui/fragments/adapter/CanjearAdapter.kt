package dev.frankperez.projectlovemyplanet.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.frankperez.projectlovemyplanet.R
import dev.frankperez.projectlovemyplanet.ui.fragments.model.CanjearModel

class CanjearAdapter (private var lstProducts: List<CanjearModel>)
    : RecyclerView.Adapter<CanjearAdapter.ViewHolder>(){
    class ViewHolder(itemsView: View): RecyclerView.ViewHolder(itemsView){
        val tv_code_Product: TextView = itemsView.findViewById(R.id.txt_cod_product)
        val tv_description_Product: TextView = itemsView.findViewById(R.id.txt_desc_product)
        val tv_stock_Product: TextView = itemsView.findViewById(R.id.txt_stock_product)
        val tv_puntos_Product: TextView = itemsView.findViewById(R.id.txt_puntos_product)
        val iv_producto: ImageView = itemsView.findViewById(R.id.img_products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_products,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemProduct = lstProducts[position]
        holder.tv_code_Product.text = itemProduct.codProducto
        holder.tv_description_Product.text = itemProduct.descProducto
        holder.tv_stock_Product.text = itemProduct.stockProducto
        holder.tv_puntos_Product.text = itemProduct.puntoProducto
        Glide.with(holder.itemView.context)
            .load(itemProduct.imgProducto)
            .into(holder.iv_producto)
    }

    override fun getItemCount(): Int {
        return lstProducts.size
    }
}
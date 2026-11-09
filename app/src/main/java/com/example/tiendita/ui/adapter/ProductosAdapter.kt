package com.example.tiendita.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendita.data.Productos
import com.example.tiendita.databinding.ItemProductosBinding

class ProductosAdapter (
    private var lista: List<Productos>
) : RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductosViewHolder {
        val binding = ItemProductosBinding.inflate(
            LayoutInflater.from(parent.context), parent,false
        )
        return ProductosViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductosViewHolder,
        position: Int
    ) {
        val productos = lista[position]
        holder.binding.tvNombreItem.text = productos.nombre
        holder.binding.tvStockPrecioItem.text = "Stock: ${productos.cantidad} | $${productos.precio}"
        holder.binding.tvCodigoItem.text = "Código: ${productos.id_producto}"
        holder.binding.tvCategoriaItem.text = "Categoría: ${productos.categoria}"

    }

    override fun getItemCount(): Int = lista.size

    class ProductosViewHolder ( val binding: ItemProductosBinding)
        : RecyclerView.ViewHolder(binding.root)
}
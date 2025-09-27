package com.example.galeriaudb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galeriaudb.R
import com.example.galeriaudb.model.Foto

class FotoAdapter : RecyclerView.Adapter<FotoAdapter.FotoVH>() {

    // Lista que contiene las fotos que vienen de la API
    private val items = mutableListOf<Foto>()

    // Método para actualizar los datos del adaptador
    fun actualizar(lista: List<Foto>) {
        items.clear()
        items.addAll(lista)
        notifyDataSetChanged()
    }

    // ViewHolder: representa una tarjeta con imagen y texto
    class FotoVH(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.tvTitulo)
        val miniatura: ImageView = view.findViewById(R.id.imgMiniatura)
    }

    // se crea cada tarjeta (viewholder) en el layout item_foto.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotoVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_foto, parent, false)
        return FotoVH(view)
    }

    // Aqui se llenan los datos de cada tajerta con los datos de la Api
    override fun onBindViewHolder(holder: FotoVH, position: Int) {
        val foto = items[position]
        holder.titulo.text = foto.author
        // Utilizamos Glide para descargar y mostrar la imagen
        Glide.with(holder.itemView.context)
            .load(foto.download_url)
            .into(holder.miniatura)

    }

    // Devuelve el número de elementos en la lista
    override fun getItemCount() = items.size
}
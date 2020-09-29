package com.example.beije.ui.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beije.R
import com.example.beije.response.Content
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

const val CONTENT_ITEM = "CONTENT_ITEM"

class MasterAdapter(private val content: List<Content>, private val gson: Gson) : RecyclerView.Adapter<DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_master_card, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        return holder.bind(content[position], gson)
    }
}

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardObject: CardView = itemView.findViewById(R.id.cardObject)
    private val title: TextView = itemView.findViewById(R.id.title_object)

    fun bind(content: Content, gson: Gson) {

        title.text = content.mediaTitleCustom

        cardObject.setOnClickListener {
            val bundle = gson.toJson(content)
            itemView.findNavController().navigate(MasterScreenDirections.actionNavigationMasterToNavigationDetail(bundle))
        }
    }
}

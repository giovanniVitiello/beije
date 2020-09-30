package com.example.beije.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.beije.R
import com.example.beije.response.Content

class HomeAdapter(private val content: MutableList<Content>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_master_card, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int = content.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(content[position], onClickListener)

    class OnClickListener(val clickListener: (Content) -> Unit) {
        fun onClick(content: Content) = clickListener(content)
    }
}

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardObject: CardView = itemView.findViewById(R.id.cardObject)
    private val title: TextView = itemView.findViewById(R.id.title_object)

    fun bind(content: Content, onClickListener: HomeAdapter.OnClickListener) {

        title.text = content.mediaTitleCustom

        cardObject.setOnClickListener {
            onClickListener.onClick(content)
        }
    }
}



package com.example.beije.ui.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beije.R
import com.example.beije.response.Content
import java.text.SimpleDateFormat
import java.util.*

class MasterAdapter(private val content: List<Content>) : RecyclerView.Adapter<DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_master_card, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        return holder.bind(content[position])
    }
}

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardObject: CardView = itemView.findViewById(R.id.cardObject)
    private val title: TextView = itemView.findViewById(R.id.title_object)

    fun bind(content: Content) {

        title.text = content.mediaTitleCustom

        cardObject.setOnClickListener {
            itemView.findNavController().navigate(MasterScreenDirections.actionNavigationMasterToNavigationDetail())
        }
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time.times(1000))
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return format.format(date)
    }
}

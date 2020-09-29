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
import com.example.beije.utils.Navigator
import java.text.SimpleDateFormat
import java.util.*

class MasterAdapter(private val content: List<Content>, private val navigator: Navigator) :
    RecyclerView.Adapter<DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_master_card, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        return holder.bind(content[position], navigator)
    }
}

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardObject: CardView = itemView.findViewById(R.id.cardObject)
    private val title: TextView = itemView.findViewById(R.id.title_object)

    fun bind(content: Content, navigator: Navigator) {

        title.text = content.mediaTitleCustom

        cardObject.setOnClickListener {
            itemView.findNavController().navigate(MasterScreenDirections.actionNavigationMasterToNavigationDetail())
//            navigator.openDetailScreen(content.mediaId) }
        }
    }
    private fun convertLongToTime(time: Long): String {
        val date = Date(time.times(1000))
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return format.format(date)
    }
}

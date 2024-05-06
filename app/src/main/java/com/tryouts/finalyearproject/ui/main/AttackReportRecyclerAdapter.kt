package com.tryouts.finalyearproject.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tryouts.finalyearproject.R
import com.tryouts.finalyearproject.data.models.AttackData
import com.tryouts.finalyearproject.databinding.AttackReportBinding

class AttackReportRecyclerAdapter(
    val list: List<AttackData>,
    val context: Context
) :
    RecyclerView.Adapter<AttackReportRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AttackReportBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.time.text = list[position].time
        holder.sensor.text = list[position].sensorNo
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.time)
        val sensor: TextView = itemView.findViewById(R.id.sensor)
    }
}
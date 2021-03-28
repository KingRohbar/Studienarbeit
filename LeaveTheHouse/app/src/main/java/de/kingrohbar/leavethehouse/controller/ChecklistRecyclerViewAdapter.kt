package de.kingrohbar.leavethehouse.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.model.Checklist

class ChecklistRecyclerViewAdapter(context: Context, checklists: ArrayList<Checklist>) : RecyclerView.Adapter<ChecklistRecyclerViewAdapter.ChecklistViewHolder>() {

    var data = checklists
    var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val layoutInflater =  LayoutInflater.from(context)
        var view: View = layoutInflater.inflate(R.layout.checklist_row, parent, false)

        return ChecklistViewHolder(view)
    }

    class ChecklistViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.checklistTitleText)
        var descriptionTextView: TextView = itemView.findViewById(R.id.checklistDescriptionText)

    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        holder.titleTextView.text = data[position].title
        holder.descriptionTextView.text = data[position].description
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
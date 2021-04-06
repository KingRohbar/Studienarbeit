package de.kingrohbar.leavethehouse.controller

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.Task
import de.kingrohbar.leavethehouse.activities.OpenChecklistActivity
import de.kingrohbar.leavethehouse.model.Checklist
import de.kingrohbar.leavethehouse.util.Finals

class ChecklistRecyclerViewAdapter(var context: Context,
                                   private var checklists: ArrayList<Checklist>
) : RecyclerView.Adapter<ChecklistRecyclerViewAdapter.ChecklistViewHolder>() {

    class ChecklistViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.checklistTitleText)
        var descriptionTextView: TextView = itemView.findViewById(R.id.checklistDescriptionText)
        var checklistRowLayout: ConstraintLayout = itemView.findViewById(R.id.checklistRowLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val layoutInflater =  LayoutInflater.from(context)
        var view: View = layoutInflater.inflate(R.layout.checklist_row, parent, false)

        return ChecklistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        holder.titleTextView.text = checklists[position].title
        holder.descriptionTextView.text = checklists[position].description

        holder.checklistRowLayout.setOnClickListener {

            val intent = Intent(context, OpenChecklistActivity::class.java)
            intent.putExtra("Title", checklists[position].title)
            intent.putExtra("Description", checklists[position].description)
            intent.putParcelableArrayListExtra("Tasks", checklists[position].tasks)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return checklists.size
    }
}
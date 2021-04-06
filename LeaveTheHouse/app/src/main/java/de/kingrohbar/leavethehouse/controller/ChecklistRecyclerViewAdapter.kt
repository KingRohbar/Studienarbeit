package de.kingrohbar.leavethehouse.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.model.Checklist

class ChecklistRecyclerViewAdapter(
    var context: Context,
    private var checklists: ArrayList<Checklist>,
    private val onChecklistListener: OnChecklistListener
) : RecyclerView.Adapter<ChecklistRecyclerViewAdapter.ChecklistViewHolder>() {

    class ChecklistViewHolder(@NonNull itemView: View, private val onChecklistListener: OnChecklistListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        var titleTextView: TextView = itemView.findViewById(R.id.checklistTitleText)
        var descriptionTextView: TextView = itemView.findViewById(R.id.checklistDescriptionText)
        var checklistRowLayout: ConstraintLayout = itemView.findViewById(R.id.checklistRowLayout)

        override fun onClick(v: View?) {
            onChecklistListener.openChecklist(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val layoutInflater =  LayoutInflater.from(context)
        var view: View = layoutInflater.inflate(R.layout.checklist_row, parent, false)

        return ChecklistViewHolder(view, onChecklistListener)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        holder.titleTextView.text = checklists[position].title
        holder.descriptionTextView.text = checklists[position].description

        /*holder.checklistRowLayout.setOnClickListener {

            val intent = Intent(context, OpenChecklistActivity::class.java)
            intent.putExtra("Title", checklists[position].title)
            intent.putExtra("Description", checklists[position].description)
            intent.putParcelableArrayListExtra("Tasks", checklists[position].tasks)
            (context as Activity).startActivityForResult(intent, Finals.GET_TASKS)
        }*/
    }

    override fun getItemCount(): Int {
        return checklists.size
    }

    interface OnChecklistListener{
        fun openChecklist(position: Int){}
    }
}
package de.kingrohbar.leavethehouse.controller

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.Task
import de.kingrohbar.leavethehouse.activities.OpenChecklistActivity

class TaskRecyclerViewAdapter (var context: Context, private var data: ArrayList<Task>) : RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder>() {

    class TaskViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.taskTitleText)
        var descriptionTextView: TextView = itemView.findViewById(R.id.taskDescriptionText)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        var taskRowLayout: ConstraintLayout = itemView.findViewById(R.id.taskRowLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater =  LayoutInflater.from(context)
        var view: View = layoutInflater.inflate(R.layout.task_row, parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.titleTextView.text = data[position].title
        holder.descriptionTextView.text = data[position].description
        holder.checkBox.isChecked = data[position].checked

        holder.taskRowLayout.setOnClickListener {
            var status = holder.checkBox.isChecked
            data[position].checked = !status
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
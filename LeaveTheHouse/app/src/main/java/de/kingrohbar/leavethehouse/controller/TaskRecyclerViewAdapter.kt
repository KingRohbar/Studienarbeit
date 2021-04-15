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
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.recyclerview.widget.RecyclerView
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.Task
import de.kingrohbar.leavethehouse.activities.OpenChecklistActivity
import java.util.*
import kotlin.collections.ArrayList

class TaskRecyclerViewAdapter (
        var context: Context,
        private var data: ArrayList<Task>,
        private val onTaskListener: OnTaskListener) : RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder>() {

    private var editMode: Boolean = false

    class TaskViewHolder(@NonNull itemView: View, private val onTaskListener: OnTaskListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        var titleTextView: TextView = itemView.findViewById(R.id.taskTitleText)
        var descriptionTextView: TextView = itemView.findViewById(R.id.taskDescriptionText)
        var lastCheckedTextView: TextView = itemView.findViewById(R.id.taskLastCheckedTextView)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        var taskRowLayout: ConstraintLayout = itemView.findViewById(R.id.taskRowLayout)
        var editMode: Boolean = false

        override fun onClick(v: View?) {
            if (!this.editMode) {
                onTaskListener.checkTask(adapterPosition)
            }else{
                onTaskListener.openTaskEdit(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater =  LayoutInflater.from(context)
        var view: View = layoutInflater.inflate(R.layout.task_row, parent, false)

        return TaskViewHolder(view, onTaskListener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.titleTextView.text = data[position].title
        holder.descriptionTextView.text = data[position].description
        holder.lastCheckedTextView.text = data[position].lastChecked
        holder.checkBox.isChecked = data[position].checked
        holder.editMode = this.editMode
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setEditMode(editMode: Boolean){
        this.editMode = editMode
        notifyDataSetChanged()
    }

    interface OnTaskListener{
        fun checkTask(position: Int){}
        fun openTaskEdit(position: Int){}
    }
}
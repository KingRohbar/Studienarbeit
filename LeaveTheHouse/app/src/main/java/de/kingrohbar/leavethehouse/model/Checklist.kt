package de.kingrohbar.leavethehouse.model

import de.kingrohbar.leavethehouse.Task

class Checklist(var name:String) {
    var description: String = "New Checklist for Leaving the House";
    private var tasks: ArrayList<Task> = ArrayList();

    fun addTask(task:Task){
        tasks.add(task);
    }

    fun removeTask(task:Task){
        tasks.remove(task);
    }
}
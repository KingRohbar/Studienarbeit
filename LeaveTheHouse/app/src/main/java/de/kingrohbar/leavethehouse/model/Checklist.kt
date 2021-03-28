package de.kingrohbar.leavethehouse.model

import de.kingrohbar.leavethehouse.Task

class Checklist(var title:String, var description: String?) {
    private var tasks: ArrayList<Task> = ArrayList();

    constructor(title: String, description: String?, task: ArrayList<Task>) : this(title, description)

    fun addTask(task:Task){
        tasks.add(task);
    }

    fun removeTask(task:Task){
        tasks.remove(task);
    }
}
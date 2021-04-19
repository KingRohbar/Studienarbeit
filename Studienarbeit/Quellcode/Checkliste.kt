class Checklist(var title:String, var description: String?) {
var tasks = ArrayList<Task>()
    constructor(title: String, description: String?, tasks: ArrayList<Task>) : this(title, description){
        this.tasks = tasks
    }

    fun addTask(task:Task){
        tasks.add(task);
    }

    fun removeTask(task:Task){
        tasks.remove(task);
    }
}
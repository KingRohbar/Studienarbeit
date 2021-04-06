package de.kingrohbar.leavethehouse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.Task
import de.kingrohbar.leavethehouse.controller.TaskRecyclerViewAdapter
import de.kingrohbar.leavethehouse.util.Finals
import kotlin.properties.Delegates

class OpenChecklistActivity : AppCompatActivity() {

    private lateinit var taskRecyclerView: RecyclerView
    lateinit var title: String
    lateinit var description: String
    private var tasks: ArrayList<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_checklist)

        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        getData()
        setData()

        val taskRecyclerViewAdapter = TaskRecyclerViewAdapter(this, this.tasks)
        taskRecyclerView.adapter = taskRecyclerViewAdapter
        taskRecyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.add_task).setOnClickListener { view ->
            val intent = Intent(this, CreateTask::class.java)
            startActivityForResult(intent, Finals.CREATE_TASK)
        }
    }

    private fun getData(){
        if(intent.hasExtra("Title") && intent.hasExtra("Description")){
            title = intent.getStringExtra("Title")
            description = intent.getStringExtra("Description")
            if (intent.hasExtra("Tasks")){
                tasks = intent.getParcelableArrayListExtra("Tasks")
            }
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
        taskRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setData(){
        this.setTitle(title)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Finals.CREATE_TASK -> {
                val bundle = data!!.extras
                if(bundle!!.get("successful") as Boolean){
                    val newTask = Task(bundle.get("title") as String, bundle.get("description") as String)
                    var uniqueTitle: Boolean = true
                    for (i in this.tasks){
                        if(i.title == newTask.title){
                            uniqueTitle = false
                        }
                    }
                    if(uniqueTitle) {
                        this.tasks.add(newTask)
                    }else{
                        Snackbar.make(findViewById(R.id.coordinaterLayoutMain), R.string.duplicateTitle, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        this.taskRecyclerView.adapter?.notifyDataSetChanged()
    }

    fun getTasks(): ArrayList<Task>{
        return tasks
    }

    override fun onStop() {
        super.onStop()
        returnTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        returnTasks()
    }

    fun returnTasks(): Intent {
        val returnIntent = Intent()
        returnIntent.putExtra("successful", true)
        returnIntent.putParcelableArrayListExtra("Tasks", this.tasks)
        setResult(Finals.GET_TASKS, returnIntent)

        return returnIntent
    }
}
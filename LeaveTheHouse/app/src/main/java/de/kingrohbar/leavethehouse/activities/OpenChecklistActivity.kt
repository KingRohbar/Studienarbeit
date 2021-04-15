package de.kingrohbar.leavethehouse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.Task
import de.kingrohbar.leavethehouse.controller.ChecklistRecyclerViewAdapter
import de.kingrohbar.leavethehouse.controller.TaskRecyclerViewAdapter
import de.kingrohbar.leavethehouse.controller.TaskRecyclerViewAdapter.*
import de.kingrohbar.leavethehouse.util.Finals
import java.lang.String.format
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class OpenChecklistActivity : AppCompatActivity(), OnTaskListener {

    private lateinit var taskRecyclerView: RecyclerView
    lateinit var title: String
    lateinit var description: String
    private var tasks: ArrayList<Task> = ArrayList()
    private lateinit var adapter: TaskRecyclerViewAdapter
    private var editMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_checklist)

        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        getData()
        setData()

        val taskRecyclerViewAdapter = TaskRecyclerViewAdapter(this, this.tasks, this)
        taskRecyclerView.adapter = taskRecyclerViewAdapter
        this.adapter = taskRecyclerViewAdapter
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
            Finals.EDIT_TASK -> {
                val bundle = data!!.extras
                if (bundle!!.get("successful") as Boolean) {

                    val newTitle = bundle.get("title") as String
                    val newDescription = bundle.get("description") as String
                    val position = bundle.getInt("Position")
                    var uniqueTitle = true
                    for (i in this.tasks.indices) {
                        if (this.tasks[i].title == newTitle && i != position) {
                            uniqueTitle = false
                        }
                    }
                    if (uniqueTitle) {
                        this.tasks[position].title = newTitle
                        this.tasks[position].description = newDescription
                    } else {
                        Snackbar.make(
                            findViewById(R.id.coordinaterLayoutMain),
                            R.string.duplicateTitle,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }else if (!(bundle!!.get("successful") as Boolean)){
                    if (bundle!!.get("delete") != null){
                        if(bundle!!.get("delete") as Boolean){
                            val position = bundle.getInt("Position")
                            this.tasks.removeAt(position)
                        }
                    }
                }
            }
        }
        this.taskRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_open_checklist, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        returnTasks()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        returnTasks()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                returnTasks()
                return true
            }
            R.id.editTasks -> {
                if (this.editMode) {
                    item.setIcon(R.drawable.baseline_mode_edit_24)
                    this.editMode = !this.editMode
                } else {
                    item.setIcon(R.drawable.baseline_edit_off_24)
                    this.editMode = !this.editMode
                }
                setEditMode(editMode)
                true
            }
            R.id.uncheckTasks -> {
                uncheckTasks()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setEditMode(editMode: Boolean){
        adapter.setEditMode(editMode)
    }

    private fun returnTasks() {
        val returnIntent = Intent()
        returnIntent.putExtra("successful", true)
        returnIntent.putExtra("checklistTitle", title)
        returnIntent.putParcelableArrayListExtra("Tasks", tasks)
        setResult(RESULT_OK, returnIntent)
        finishActivity(Finals.GET_TASKS)
        finish()
    }

    override fun checkTask(position: Int) {
        tasks[position].checked = !tasks[position].checked
        if(tasks[position].lastChecked == "" && tasks[position].checked){
            tasks[position].lastChecked = formatDate()
        }else{
            tasks[position].lastChecked = ""
        }
        this.taskRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun formatDate(): String{
        var dateTime = Calendar.getInstance().time
        var dateFormat = SimpleDateFormat("E, dd.MM.yy HH:mm")
        var formattedDate = dateFormat.format(dateTime).toString()

        return formattedDate
    }

    private fun uncheckTasks(){
        for (i in tasks){
            i.checked = false
            i.lastChecked = ""
        }
        adapter.notifyDataSetChanged()
    }

    override fun openTaskEdit(position: Int) {
        super.openTaskEdit(position)
        val intent = Intent(this, EditTask::class.java)
        intent.putExtra("Title", tasks[position].title)
        intent.putExtra("Description", tasks[position].description)
        intent.putExtra("Position", position)
        startActivityForResult(intent, Finals.EDIT_TASK)
    }
}
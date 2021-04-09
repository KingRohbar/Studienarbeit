package de.kingrohbar.leavethehouse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.util.Finals

class EditTask : AppCompatActivity(), DeleteTaskDialog.DeleteTaskDialogListener {

    private var taskPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        setSupportActionBar(findViewById(R.id.toolbar))

        val editTaskFab = findViewById<FloatingActionButton>(R.id.editTaskFab)
        var taskTitle : TextView = findViewById<EditText>(R.id.titleInputTask)
        val taskDescription : TextView = findViewById<EditText>(R.id.descriptionInputTask)
        taskPosition = intent.getIntExtra("Position",0)


        if(intent.hasExtra("Title")){
            taskTitle.text = intent.getStringExtra("Title").toString()
            if (intent.hasExtra("Description")){
                taskDescription.text = intent.getStringExtra("Description")
            }
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }


        editTaskFab.setOnClickListener{
            val title = findViewById<EditText>(R.id.titleInputTask).text.toString()
            val description = findViewById<EditText>(R.id.descriptionInputTask).text.toString()

            intent = Intent()
            intent.putExtra("successful", true)
            intent.putExtra("title", title)
            intent.putExtra("description", description)
            intent.putExtra("Position", taskPosition)

            setResult(Finals.EDIT_TASK, intent)
            finishActivity(Finals.EDIT_TASK)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_task_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteTask -> {
                openDeleteDialog()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun openDeleteDialog(){
        val deleteTaskDialog = DeleteTaskDialog()
        deleteTaskDialog.show(supportFragmentManager, "deleteTaskDialog")
    }

    override fun getDialogAnswer(answer: Boolean) {
        intent = Intent()
        intent.putExtra("successful", false)
        intent.putExtra("delete", answer)
        intent.putExtra("Position", taskPosition)
        setResult(Finals.EDIT_TASK, intent)
        finishActivity(Finals.EDIT_TASK)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        intent = Intent()
        intent.putExtra("successful", false)
        setResult(Finals.EDIT_TASK, intent)
        finishActivity(Finals.EDIT_TASK)
        finish()
        super.onBackPressed()
    }
}
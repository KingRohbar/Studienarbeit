package de.kingrohbar.leavethehouse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.util.Finals

class CreateTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
        setSupportActionBar(findViewById(R.id.toolbar))

        val createTaskFab = findViewById<FloatingActionButton>(R.id.createTaskFab)

        createTaskFab.setOnClickListener{
            val title = findViewById<EditText>(R.id.titleInputTask).text.toString()
            val description = findViewById<EditText>(R.id.descriptionInputTask).text.toString()

            intent = Intent()
            intent.putExtra("successful", true)
            intent.putExtra("title", title)
            intent.putExtra("description", description)

            setResult(Finals.CREATE_TASK, intent)
            finishActivity(Finals.CREATE_TASK)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        intent = Intent()
        intent.putExtra("successful", false)
        setResult(Finals.CREATE_TASK, intent)
        finishActivity(Finals.CREATE_TASK)
        finish()
        super.onBackPressed()
    }
}
package de.kingrohbar.leavethehouse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.util.Finals

class EditChecklist : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_checklist)
        setSupportActionBar(findViewById(R.id.toolbar))

        val editChecklistFab = findViewById<FloatingActionButton>(R.id.editChecklistFab)
        var checklistTitle : TextView = findViewById<EditText>(R.id.titleInput)
        val checklistDescription : TextView = findViewById<EditText>(R.id.descriptionInput)
        val checklistPosition = intent.getIntExtra("Position", 0)

        if(intent.hasExtra("Title")){
            checklistTitle.text = intent.getStringExtra("Title").toString()
            if (intent.hasExtra("Description")){
                checklistDescription.text = intent.getStringExtra("Description")
            }
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }


        editChecklistFab.setOnClickListener{
            val title = findViewById<EditText>(R.id.titleInput).text.toString()
            val description = findViewById<EditText>(R.id.descriptionInput).text.toString()

            intent = Intent()
            intent.putExtra("successful", true)
            intent.putExtra("title", title)
            intent.putExtra("description", description)
            intent.putExtra("Position", checklistPosition)

            setResult(Finals.EDIT_CHECKLIST, intent)
            finishActivity(Finals.EDIT_CHECKLIST)
            finish()
        }
    }
}
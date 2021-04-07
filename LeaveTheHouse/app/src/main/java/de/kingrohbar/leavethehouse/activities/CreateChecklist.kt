package de.kingrohbar.leavethehouse.activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import de.kingrohbar.leavethehouse.R
import de.kingrohbar.leavethehouse.util.Finals

class CreateChecklist : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_checklist)
        setSupportActionBar(findViewById(R.id.toolbar))

        val createChecklistFab = findViewById<FloatingActionButton>(R.id.createChecklistFab)

        createChecklistFab.setOnClickListener{
            val title = findViewById<EditText>(R.id.titleInput).text.toString()
            val description = findViewById<EditText>(R.id.descriptionInput).text.toString()

            intent = Intent()
            intent.putExtra("successful", true)
            intent.putExtra("title", title)
            intent.putExtra("description", description)

            setResult(Finals.CREATE_CHECKLIST, intent)
            finishActivity(Finals.CREATE_CHECKLIST)
            finish()
        }
    }

    override fun onBackPressed() {
        intent = Intent()
        intent.putExtra("successful", false)
        setResult(Finals.CREATE_CHECKLIST, intent)
        finishActivity(Finals.CREATE_CHECKLIST)
        finish()
        super.onBackPressed()
    }
}
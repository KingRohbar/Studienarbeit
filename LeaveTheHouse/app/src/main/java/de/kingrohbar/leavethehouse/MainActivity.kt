package de.kingrohbar.leavethehouse

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import de.kingrohbar.leavethehouse.fragment.CreateChecklist
import de.kingrohbar.leavethehouse.util.Finals

class MainActivity : AppCompatActivity() {

    private var isLargeLayout: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        isLargeLayout = resources.getBoolean(R.bool.large_layout)

        findViewById<FloatingActionButton>(R.id.add_checklist).setOnClickListener { view ->
            val intent = Intent(this, CreateChecklist::class.java)
            startActivityForResult(intent, Finals.CREATE_CHECKLIST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
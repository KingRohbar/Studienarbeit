package de.kingrohbar.leavethehouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import de.kingrohbar.leavethehouse.controller.ChecklistRecyclerViewAdapter
import de.kingrohbar.leavethehouse.fragment.CreateChecklist
import de.kingrohbar.leavethehouse.model.Checklist
import de.kingrohbar.leavethehouse.util.Finals
import org.json.JSONObject
import java.io.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private var FILENAME = "storage.json"
    private var isLargeLayout: Boolean = false
    private var data: ArrayList<Checklist> = ArrayList()
    private lateinit var checklistRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        isLargeLayout = resources.getBoolean(R.bool.large_layout)
        checklistRecyclerView = findViewById(R.id.checklistRecyclerView)
        getChecklistsFromFile(this)

        val checklistRecyclerViewAdapter = ChecklistRecyclerViewAdapter(this, this.data)
        checklistRecyclerView.adapter = checklistRecyclerViewAdapter
        checklistRecyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.add_checklist).setOnClickListener { view ->
            val intent = Intent(this, CreateChecklist::class.java)
            startActivityForResult(intent, Finals.CREATE_CHECKLIST)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveChecklistsToFile(this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Finals.CREATE_CHECKLIST -> {
                val bundle = data!!.extras
                if(bundle!!.get("successful") as Boolean){
                    val newChecklist = Checklist(bundle.get("title") as String, bundle.get("description") as String)
                    var uniqueTitle: Boolean = true
                    for (i in this.data){
                        if(i.title == newChecklist.title){
                            uniqueTitle = false
                        }
                    }
                    if(uniqueTitle) {
                        this.data.add(newChecklist)
                    }else{
                        Snackbar.make(findViewById(R.id.coordinaterLayout), R.string.duplicateTitle, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        checklistRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun saveChecklistsToFile(context: Context){

        var checklistsString: String = /*"{ \n \"checklists\":*/ "{\n"

        for (i in this.data.indices){
            val checklistJson = JSONObject()
            checklistJson.put("title", this.data[i].title)
            checklistJson.put("description", this.data[i].description)
            var checklistString = checklistJson.toString()

            checklistsString += "\"$i\": $checklistString"
            if(i + 1 != this.data.size){
                checklistsString += ","
            }
        }
        checklistsString += "\n}"/*\n}"*/

        var file = File(context.filesDir, FILENAME)
        var fileWriter = FileWriter(file)
        var bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(checklistsString)
        bufferedWriter.close()
    }

    private fun getChecklistsFromFile(context: Context){

        var file = File(context.filesDir, FILENAME)
        var fileReader = FileReader(file)
        var bufferedReader = BufferedReader(fileReader)
        var stringBuilder = StringBuilder()
        var line = bufferedReader.readLine()

        while (line != null){
            stringBuilder.append(line).append("\n")
            line = bufferedReader.readLine()
        }
        bufferedReader.close()

        var response: String = stringBuilder.toString()
        val checklistsJson = JSONObject(response)
        Log.d("checklsitsJson", checklistsJson.toString())

        for(i in checklistsJson.keys()){
            Log.d("JsonObject content", checklistsJson[i].toString())
            val jsonObject = JSONObject(checklistsJson[i].toString())
            this.data.add(Checklist(jsonObject.getString("title"), jsonObject.getString("description")))
        }
    }
}
package de.kingrohbar.leavethehouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import de.kingrohbar.leavethehouse.activities.CreateChecklist
import de.kingrohbar.leavethehouse.activities.EditChecklist
import de.kingrohbar.leavethehouse.activities.OpenChecklistActivity
import de.kingrohbar.leavethehouse.controller.ChecklistRecyclerViewAdapter
import de.kingrohbar.leavethehouse.model.Checklist
import de.kingrohbar.leavethehouse.util.Finals
import org.json.JSONArray
import org.json.JSONObject
import java.io.*


class MainActivity : AppCompatActivity(), ChecklistRecyclerViewAdapter.OnChecklistListener{

    private var FILENAME = "storage.json"
    private var isLargeLayout: Boolean = false
    private var editMode: Boolean = false
    private lateinit var adapter: ChecklistRecyclerViewAdapter
    private var data: ArrayList<Checklist> = ArrayList()
    private lateinit var checklistRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        isLargeLayout = resources.getBoolean(R.bool.large_layout)
        checklistRecyclerView = findViewById(R.id.checklistRecyclerView)
        getChecklistsFromFile(this)
        //printData()

        val checklistRecyclerViewAdapter = ChecklistRecyclerViewAdapter(this, this.data, this)
        checklistRecyclerView.adapter = checklistRecyclerViewAdapter
        this.adapter = checklistRecyclerViewAdapter
        checklistRecyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.add_checklist).setOnClickListener { view ->
            val intent = Intent(this, CreateChecklist::class.java)
            startActivityForResult(intent, Finals.CREATE_CHECKLIST)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        saveChecklistsToFile(this)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        saveChecklistsToFile(this)
        super.onStop()
    }

    override fun onBackPressed() {
        saveChecklistsToFile(this)
        super.onBackPressed()
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
            R.id.edit -> {
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Finals.CREATE_CHECKLIST -> {
                val bundle = data!!.extras
                if (bundle!!.get("successful") as Boolean) {
                    val newChecklist = Checklist(
                        bundle.get("title") as String,
                        bundle.get("description") as String
                    )
                    var uniqueTitle: Boolean = true
                    for (i in this.data) {
                        if (i.title == newChecklist.title) {
                            uniqueTitle = false
                        }
                    }
                    if (uniqueTitle) {
                        this.data.add(newChecklist)
                    } else {
                        Snackbar.make(
                            findViewById(R.id.coordinaterLayoutMain),
                            R.string.duplicateTitle,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            Finals.EDIT_CHECKLIST -> {
                val bundle = data!!.extras
                if (bundle!!.get("successful") as Boolean) {

                    val newTitle = bundle.get("title") as String
                    val newDescription = bundle.get("description") as String
                    val position = bundle.getInt("Position")
                    var uniqueTitle = true
                    for (i in this.data.indices) {
                        if (this.data[i].title == newTitle && i != position) {
                            uniqueTitle = false
                        }
                    }
                    if (uniqueTitle) {
                        this.data[position].title = newTitle
                        this.data[position].description = newDescription
                    } else {
                        Snackbar.make(
                            findViewById(R.id.coordinaterLayoutMain),
                            R.string.duplicateTitle,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }else if (!(bundle!!.get("successful") as Boolean)){
                    if (bundle!!.getBoolean("delete") != null){
                        if(bundle!!.get("delete") as Boolean){
                            val position = bundle.getInt("Position")
                            this.data.removeAt(position)
                        }
                    }
                }
            }
            Finals.GET_TASKS -> {
                val bundle = data!!.extras
                if (bundle!!.get("successful") as Boolean) {
                    for (i in this.data) {
                        if (i.title == bundle.get("checklistTitle")) {
                            i.tasks = bundle.getParcelableArrayList<Task>("Tasks") as ArrayList<Task>
                        }
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
            var tasksJson = JSONArray()
            for (j in this.data[i].tasks.indices){
                var taskJson = JSONObject()
                taskJson.put("title", this.data[i].tasks[j].title)
                taskJson.put("description", this.data[i].tasks[j].description)
                taskJson.put("checked", this.data[i].tasks[j].checked)
                taskJson.put("lastChecked", this.data[i].tasks[j].lastChecked)
                tasksJson.put(taskJson)
            }
            checklistJson.put("tasks", tasksJson)
            var checklistString = checklistJson.toString()
            //Log.d("Tasks", "saveChecklistsToFile: $checklistString")

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

        if(file.exists()) {

            var fileReader = FileReader(file)
            var bufferedReader = BufferedReader(fileReader)
            var stringBuilder = StringBuilder()
            var line = bufferedReader.readLine()

            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = bufferedReader.readLine()
            }
            bufferedReader.close()

            var response: String = stringBuilder.toString()
            val checklistsJson = JSONObject(response)
            //Log.d("checklistsJson", checklistsJson.toString())


            for (i in checklistsJson.keys()) {
                //Log.d("JsonObject content", checklistsJson[i].toString())
                val jsonObject = JSONObject(checklistsJson[i].toString())
                var tasksJson = jsonObject.getJSONArray("tasks")
                var tasks = ArrayList<Task>()
                //Log.d("JsonObject tasks", tasksJson.toString())
                for (j in 0 until tasksJson.length()) {
                    var jsonTaskObject = tasksJson.getJSONObject(j)
                    //Log.d("JsonObject task", jsonTaskObject.toString())
                    tasks.add(
                        Task(
                            jsonTaskObject.getString("title"),
                            jsonTaskObject.getString("description"),
                            jsonTaskObject.getBoolean("checked"),
                                jsonTaskObject.getString("lastChecked")
                        )
                    )
                }

                this.data.add(
                    Checklist(
                        jsonObject.getString("title"),
                        jsonObject.getString("description"),
                        tasks
                    )
                )
            }
        }
    }

    override fun openChecklist(position: Int) {
        super.openChecklist(position)
        val intent = Intent(this, OpenChecklistActivity::class.java)
        intent.putExtra("Title", data[position].title)
        intent.putExtra("Description", data[position].description)
        intent.putParcelableArrayListExtra("Tasks", data[position].tasks)
        startActivityForResult(intent, Finals.GET_TASKS)
    }

    override fun openChecklistEdit(position: Int) {
        super.openChecklistEdit(position)
        val intent = Intent(this, EditChecklist::class.java)
        intent.putExtra("Title", data[position].title)
        intent.putExtra("Description", data[position].description)
        intent.putExtra("Position", position)
        startActivityForResult(intent, Finals.EDIT_CHECKLIST)
    }

    private fun setEditMode(editMode: Boolean){
        adapter.setEditMode(editMode)
    }

    private fun printData() {
        var dataString: String = ""
        for (i in data.indices){
            dataString += "Title: ${data[i].title}, Description: ${data[i].description}, Tasks: "
            for (j in data[i].tasks.indices) {
             dataString += "Title: ${data[i].tasks[j].title}, Description: ${data[i].tasks[j].description}, "
            }
            dataString += "; "
        }
        Log.d("PrintData", dataString)
    }
}
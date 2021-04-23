private fun saveChecklistsToFile(context: Context){

    var checklistsString: String = "{\n"

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
        checklistsString += "\"$i\": $checklistString"
        if(i + 1 != this.data.size){
            checklistsString += ","
        }
    }
    checklistsString += "\n}"

    var file = File(context.filesDir, FILENAME)
    var fileWriter = FileWriter(file)
    var bufferedWriter = BufferedWriter(fileWriter)
    bufferedWriter.write(checklistsString)
    bufferedWriter.close()
}

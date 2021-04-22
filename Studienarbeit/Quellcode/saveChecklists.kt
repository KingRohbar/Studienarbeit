private fun saveChecklistsToFile(context: Context){

        var checklistsString: String = "{\n"

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
        checklistsString += "\n}"

        var file = File(context.filesDir, FILENAME)
        var fileWriter = FileWriter(file)
        var bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(checklistsString)
        bufferedWriter.close()
    }
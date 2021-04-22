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
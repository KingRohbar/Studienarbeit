findViewById<FloatingActionButton>(R.id.add_checklist).setOnClickListener { view ->
            val intent = Intent(this, CreateChecklist::class.java)
            startActivityForResult(intent, Finals.CREATE_CHECKLIST)
        }
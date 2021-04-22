override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
    val layoutInflater =  LayoutInflater.from(context)
    var view: View = layoutInflater.inflate(R.layout.checklist_row, parent, false)

    return ChecklistViewHolder(view, onChecklistListener)
}

override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
    holder.titleTextView.text = checklists[position].title
    holder.descriptionTextView.text = checklists[position].description
    holder.editMode = this.editMode
}
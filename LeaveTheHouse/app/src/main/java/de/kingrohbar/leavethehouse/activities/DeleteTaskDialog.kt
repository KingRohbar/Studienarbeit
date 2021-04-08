package de.kingrohbar.leavethehouse.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import java.lang.ClassCastException

class DeleteTaskDialog: AppCompatDialogFragment() {

    private var delete: Boolean = false
    private lateinit var deleteTaskDialogListener: DeleteTaskDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Warning")
            .setMessage("Do you really want to delete this task?")
            .setNegativeButton("Cancel"){dialog, which ->
            }
            .setPositiveButton("Yes") { dialog, which ->
                this.delete = true
                deleteTaskDialogListener.getDialogAnswer(delete)
            }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            deleteTaskDialogListener = context as DeleteTaskDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement DeleteTaskDialogListener")
        }
    }

    interface DeleteTaskDialogListener{
        fun getDialogAnswer(answer: Boolean){}
    }
}
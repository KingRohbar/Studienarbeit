package de.kingrohbar.leavethehouse.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import java.lang.ClassCastException

class DeleteChecklistDialog: AppCompatDialogFragment() {

    private var delete: Boolean = false
    private lateinit var deleteChecklistDialogListener: DeleteChecklistDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Warning")
            .setMessage("Do you really want to delete this checklist?")
            .setNegativeButton("Cancel"){dialog, which ->
            }
            .setPositiveButton("Yes") { dialog, which ->
                this.delete = true
                deleteChecklistDialogListener.getDialogAnswer(delete)
            }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            deleteChecklistDialogListener = context as DeleteChecklistDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement DeleteChecklistDialogListener")
        }
    }

    interface DeleteChecklistDialogListener{
        fun getDialogAnswer(answer: Boolean){}
    }
}
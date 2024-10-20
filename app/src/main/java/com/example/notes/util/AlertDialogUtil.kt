package com.example.notes.util

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.notes.R

object AlertDialogUtil {

    fun createDialogToDeleteNote(activity: FragmentActivity, deleteFun:()->Unit) {
        val alertDialog = AlertDialog.Builder(activity).apply {
            setTitle(activity.getString(R.string.dialog_title_delete))
            setMessage(activity.getString(R.string.dialog_message_delete))
            setNegativeButton(activity.getString(R.string.cancel), null)
            val positiveButtonListener =
                DialogInterface.OnClickListener { p0, p1 -> deleteFun.invoke() }
            setPositiveButton(activity.getString(R.string.delete), positiveButtonListener)
        }
        alertDialog.show()
    }

}
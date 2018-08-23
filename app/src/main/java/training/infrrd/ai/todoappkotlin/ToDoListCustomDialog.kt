package training.infrrd.ai.todoappkotlin

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.android.synthetic.main.custom_add_dialog.view.*

class ToDoListCustomDialog(): DialogFragment() {

    var callback: (String) -> Unit = {}

    fun assigncallback(callback: (String) -> Unit) {
        this.callback = callback

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builderDialog = AlertDialog.Builder(activity)
        val layoutInflater = LayoutInflater.from(activity).inflate(R.layout.custom_add_dialog, null)
        builderDialog.setView(layoutInflater)
        builderDialog.setMessage("Add a new To Do")

        builderDialog.setPositiveButton("YES"){
            dialog, which ->
            this.callback.invoke(layoutInflater.user_entered_title.text.toString())
        }
        builderDialog.setNegativeButton("No"){
            dialog, which ->
            Toast.makeText(activity, "Negative button was clicked", Toast.LENGTH_SHORT).show()
        }
        val builder = builderDialog.create()
        return builder
    }
}
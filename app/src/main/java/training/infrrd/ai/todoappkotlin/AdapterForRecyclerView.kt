package training.infrrd.ai.todoappkotlin

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.ActionBar
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.custom_card_layout.view.*


class AdapterForRecyclerView(val context: Context, val items:ArrayList<String>, val realm:Realm):RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_card_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.holderDataTitle.text = items.get(position)

        holder.cardButton.setOnClickListener {
            Log.d("TAG", "card button clicked")
        }

        holder.cardButton.setOnLongClickListener{
            Log.d("Tag", "clicked for long")
            val builderDialog = AlertDialog.Builder(context)
            builderDialog.setTitle("Delete Item?")
            builderDialog.setPositiveButton("Yes"){
                dialogInterface, i ->
                realm.beginTransaction()
                val realmObjectToDelete = realm.where(ToDoListRealmObject::class.java).equalTo("title", items.get(position)).findAll()
                realmObjectToDelete.deleteAllFromRealm()
                realm.commitTransaction()
                items.removeAt(position)
                notifyItemRemoved(position)
            }
            builderDialog.setNegativeButton("No"){
                dialogInterface, i ->
            }

            val dialog = builderDialog.create()
            dialog.show()
            return@setOnLongClickListener true
        }

        holder.doneCheckBox.setOnCheckedChangeListener{buttonView, isChecked ->
            val dynamicWidth = holder.cardButton.width/2
            if(isChecked){
                holder.horizontal_line_for_done.visibility = View.VISIBLE
                holder.horizontal_line_for_done.pivotX = 0f
                ObjectAnimator.ofFloat(holder.horizontal_line_for_done, "scaleX", dynamicWidth.toFloat()).apply {
                    duration = 1000
                    start()
                }
            } else{
                ObjectAnimator.ofFloat(holder.horizontal_line_for_done, "scaleX", 0f).apply {
                    duration = 1000
                    start()
                }
            }
        }
    }
}

class ViewHolder(view:View):RecyclerView.ViewHolder(view){
    val holderDataTitle = view.data_holder_for_title
    val cardButton = view.relative_layout_for_card
    val doneCheckBox = view.done_check_box
    val horizontal_line_for_done = view.horizontal_line_for_done
}
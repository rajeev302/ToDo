package training.infrrd.ai.todoappkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.android.synthetic.main.activity_main.floating_button

class MainActivity : AppCompatActivity() {

    var items:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)

        val config = RealmConfiguration.Builder().name("toDolist.realm").build()

        var realm = Realm.getInstance(config)

        val allToDoListRealmObject = realm.where(ToDoListRealmObject::class.java).findAll()

        allToDoListRealmObject.forEach { data ->
            items.add(data.title)
        }

        val adapterForRecyclerView = AdapterForRecyclerView(this, items, realm)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapterForRecyclerView

        val toDoListCustomDialog = ToDoListCustomDialog()

        addData(realm, toDoListCustomDialog, adapterForRecyclerView)

        floating_button.setOnClickListener{
            toDoListCustomDialog.show(this@MainActivity.fragmentManager, "custom Dialog Manager")
        }

    }

    fun addData(realm: Realm, toDoListCustomDialog:ToDoListCustomDialog, adapterForRecyclerView:AdapterForRecyclerView){
        toDoListCustomDialog.assigncallback { title: String ->
            realm.beginTransaction()
            var toDoListRealmObject:ToDoListRealmObject
            var maxId = realm.where(ToDoListRealmObject::class.java).max("id")
            if(maxId == null){
                toDoListRealmObject = realm.createObject(ToDoListRealmObject::class.java, 1)
                toDoListRealmObject.title = title
            }
            else{
                toDoListRealmObject = realm.createObject(ToDoListRealmObject::class.java, maxId.toInt() + 1)
                toDoListRealmObject.title = title
            }
            realm.commitTransaction()
            items.add(title)
            adapterForRecyclerView.notifyDataSetChanged()
        }
    }
}




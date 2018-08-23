package training.infrrd.ai.todoappkotlin

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ToDoListRealmObject():RealmObject() {
    @PrimaryKey
    var id:Int = 0
    var title:String = ""
}
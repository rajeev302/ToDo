package training.infrrd.ai.todoappkotlin

class ToDoDataModel {
    private var title:String = ""
    get() = field
    set(value) {
        field = value
    }
    private var checkedStatus:Boolean = false
}
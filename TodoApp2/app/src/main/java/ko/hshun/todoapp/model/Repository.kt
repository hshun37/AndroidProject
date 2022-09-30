package ko.hshun.todoapp.model

import android.content.Context
import ko.hshun.todoapp.model.room.TodoDatabase
import ko.hshun.todoapp.model.room.TodoEntity

class Repository(context: Context) {
    val db = TodoDatabase.getDatabase(context)

    fun getTextList() = db.todoDao().getAllData()

    fun insertTextData(text: String) = db.todoDao().insert(TodoEntity(0, text))

    fun deleteTextData() = db.todoDao().deleteAllData()
}
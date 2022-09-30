package ko.hshun.todoapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import ko.hshun.todoapp.model.Repository
import ko.hshun.todoapp.model.room.TodoDatabase
import ko.hshun.todoapp.model.room.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app:Application) : AndroidViewModel(app) {

    val context = getApplication<Application>().applicationContext
    val db = TodoDatabase.getDatabase(context)

    private var _todoList = MutableLiveData<List<TodoEntity>>()
    val todoList : LiveData<List<TodoEntity>>
        get() = _todoList

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        Log.d("MainViewModel", db.todoDao().getAllData().toString())
        _todoList.postValue(db.todoDao().getAllData())
    }

    fun insert(text: String) = viewModelScope.launch(Dispatchers.IO) {
        db.todoDao().insert(TodoEntity(0, text))
    }
}
package ko.hshun.todoapp.model.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM todolist")
    fun getLiveAllData(): LiveData<List<TodoEntity>>

    @Query("SELECT * FROM todolist")
    fun getAllData(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(text: TodoEntity)

    @Query("DELETE FROM todolist")
    fun deleteAllData()

    @Delete
    fun deleteSelect(TodoEntity: TodoEntity)

}
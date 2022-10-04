package ko.hshun.todoapp.model.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM todolist")
    fun getLiveAllData(): LiveData<List<TodoEntity>>

    @Query("SELECT * FROM todolist")
    fun getAllData(): List<TodoEntity>

    @Query("DELETE FROM todolist")
    fun deleteAllData()

    @Query("UPDATE todolist SET `check` = :check WHERE id = :id")
    fun update(check: Boolean, id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(text: TodoEntity)

    @Delete
    fun deleteSelect(TodoEntity: TodoEntity)

}
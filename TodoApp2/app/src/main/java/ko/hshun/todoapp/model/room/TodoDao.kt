package ko.hshun.todoapp.model.room

import androidx.room.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM todolist")
    fun getAllData(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(text: TodoEntity)

    @Query("DELETE FROM todolist")
    fun deleteAllData()

//    @Query("DELETE FROM todolist WHERE ")

}
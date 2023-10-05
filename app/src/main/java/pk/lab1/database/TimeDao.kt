package pk.lab1.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TimeDao {
    @Query("SELECT * FROM time")
    fun getAll(): List<Time>

    @Insert
    fun insertAll(vararg times: Time)

    @Delete
    fun delete(time: Time)
}
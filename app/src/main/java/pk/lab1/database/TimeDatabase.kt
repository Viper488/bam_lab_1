package pk.lab1.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Time::class], version = 1)
abstract class TimeDatabase : RoomDatabase() {
    abstract fun timeDao(): TimeDao
}
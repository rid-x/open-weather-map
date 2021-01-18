package org.openweathermap.weather.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import org.openweathermap.weather.data.db.entity.DayEntity

@Dao
interface DayDao {

    @Query("SELECT * FROM dayentity")
    fun getAll(): List<DayEntity>

    @Insert
    fun insertAll(vararg days: DayEntity)

    @Delete
    fun delete(day: DayEntity)

    @Query("DELETE FROM dayentity")
    fun dropTable()
}
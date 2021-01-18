package org.openweathermap.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.openweathermap.weather.data.db.dao.DayDao
import org.openweathermap.weather.data.db.entity.DayEntity
import org.koin.dsl.module

val dbModule = module {
    single { provideDb(get()) }
    factory { provideDayDao(get()) }
}

private const val DB_NAME = "db-weather"

fun provideDb(applicationContext: Context): AppDatabase {
    return Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, DB_NAME
    ).build()
}

fun provideDayDao(appDatabase: AppDatabase): DayDao {
    return appDatabase.dayDao()
}

@Database(entities = [DayEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
}
package com.example.sqliteapplication.room.student

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudentEntity::class], version = 1)
abstract class StudentDatabase : RoomDatabase(){

    companion object {
        fun getAppDatabase(context: Context): StudentDatabase =
            Room.databaseBuilder(context, StudentDatabase::class.java, "db_app").build()
    }

    abstract fun studentDao(): StudentDao
}
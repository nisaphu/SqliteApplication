package com.example.sqliteapplication.room.student

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class StudentEntity (@PrimaryKey(autoGenerate = true)var id :Int? = null,
                     @ColumnInfo(name = "student_code")var code: Long? = null,
                     @ColumnInfo(name = "first_name") var firstName: String? = null,
                     @ColumnInfo(name = "last_name") var lastName: String? = null,
                     var email: String? = null,
                     var gender: String? = null,
                     var address: String? = null)
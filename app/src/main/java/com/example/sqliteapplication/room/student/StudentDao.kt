package com.example.sqliteapplication.room.student

import androidx.room.*

@Dao
interface StudentDao {
    @Insert
    fun insertStudent(studentEntity: StudentEntity)

    @Update
    fun updateStudent(studentEntity: StudentEntity)

    @Delete
    fun deleteStudent(studentEntity: StudentEntity)

    @Query("SELECT * FROM student")
    fun getStudentAll(): List<StudentEntity>

    @Query("SELECT * FROM student WHERE student.student_code = :studentCode")
    fun getStudentByCode(studentCode: Int): List<StudentEntity>

    @Query("SELECT * FROM student WHERE student.email LIKE :email")
    fun getStudentByEmail(email: String): StudentEntity

    @Query("DELETE FROM student")
    fun deleteTable()
}
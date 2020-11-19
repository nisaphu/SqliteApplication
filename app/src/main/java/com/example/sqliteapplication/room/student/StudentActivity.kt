package com.example.sqliteapplication.room.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sqliteapplication.R
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {
    private val TAG = "StudentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        initWidget()
    }

    private fun initWidget(){
        val appDatabase = StudentDatabase.getAppDatabase(this)

        val scoopsStudent = StudentEntity()
        scoopsStudent.code = 1234567891
        scoopsStudent.email = "nisa@gmail.com"
        scoopsStudent.firstName = "nisa"
        scoopsStudent.lastName = "put"
        scoopsStudent.address = "110/4 Moo 5, Canal Road, Suthep, Muang Chiang Mai, Chiang Mai 50200"

        Flowable.fromCallable { appDatabase.studentDao().insertStudent(scoopsStudent) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{Log.d(TAG, "insert complete")}

        Flowable.fromCallable { appDatabase.studentDao().getStudentAll()}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{it.forEach { textView.text = it.firstName }}
    }
}

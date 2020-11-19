package com.example.sqliteapplication.actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqliteapplication.R
import okhttp3.internal.immutableListOf

class NullSafetyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_null_safety)
    }

    private fun one() {
        // Nullable
        var name1: String? = "Black Lens"

        // Not Null
        var name2: String = "Black Lens"

        // OK
        name1 = null
        // Error
        name2 = null
    }

    private fun two() {

        val name: String? = "Test"
        // Error
        updateNewName(name)

        // Crash with NullPointerException
        updateNewName(name!!)
        name.toUpperCase()

        try {
            updateNewName(name!!)
        } catch (e: NullPointerException) {
            // Handle code when error
        }

        updateNewName(name ?: "")

        if (name != null) {
            updateNewName(name)
        }

        name?.let {
            updateNewName(name)
        }

        name!!.let {
            updateNewName(name)
        } ?: run {
            // Handle code when name is null
        }

    }

    fun updateNewName(name: String) {
        // Update new name to web server
    }

    data class User(val name: String, val age: Int)

    val list = mutableListOf(
        User("gulf", 15),
        User("mew", 29),
        User("yin", 21),
        User("war", 26)
    )

    private fun all() {
        val isAllUserFifthTeenYearsOld = list.all {
            it.age == 15
        }
        println("$isAllUserFifthTeenYearsOld")
        //ผลลัพธ์ false
    }

    private fun any() {
        //Before
        val any = list.size > 0
        //After
        list.any()

        val check = list.any { it.age == 15 }
        println("$check")
        //ผลลัพธ์ true
    }

    private fun contains() {
        val text = "https://www.satchan48.com"
        val isUrl = text.contains("http")
        println("$isUrl")
        //ผลลัพธ์ true
    }

    private fun count() {
        val count = list.count { it.age in 15..19 }
        println("$count")
        //ผลลัพธ์ 1
    }

    private fun filter() {
        val newList = list.filter { it.name == "gulf" }
        println("$newList")
        //ผลลัพธ์ [User(name=gulf, age=15)]
    }

    private fun find() {
        val user = list.find { it.name == "emma" }
        println("$user")
        //ผลลัพธ์ null
    }

    private fun firstOrNull() {
        val user = list.firstOrNull { it.name == "emma" }
        println("$user")
        //ผลลัพธ์ null
    }

    private fun none() {
        //Before
        val none = list.size == 0
        //After
        list.none()

        val check = list.none { it.age == 14 }
        println("$check")
        //ผลลัพธ์ true
    }

    private fun orEmpty() {
        //Before
        val text: String? = null

        if(text == null){
            return ""
        }

        val nonNullText: String = text ?: ""
        //After
        val text1: String? = null
        val nonNullText1: String = text1.orEmpty()
    }

    private fun sortBy() {
        list.sortBy { it.age }
        println("$list")
        //ผลลัพธ์ [User(name=satchan, age=15), User(name=jaa, age=16), User(name=nink, age=19), User(name=cherprang, age=23)]
    }

    private fun sortByDescending() {
        list.sortByDescending { it.age }
        println("$list")
        //ผลลัพธ์ [User(name=cherprang, age=23), User(name=nink, age=19), User(name=jaa, age=16), User(name=satchan, age=15)]
    }

    private fun toString1() {
        var text = null
        println(text.toString())
        //ผลลัพธ์ null

    }

    private fun mutableList(){
        val idolList = mutableListOf("Satchan","Friendly Saturn")
        println(idolList.toString())
        //ผลลัพธ์ [Satchan, Friendly Saturn]
    }

    private fun dataClass(){
        val myIdol = User("satchan",15)
        println(myIdol.toString())
        //ผลลัพธ์ User(name=satchan, age=15)
    }

    private fun lastIndex(){
        //Before
        val lastIndex = list.size -1
        //After
        list.lastIndex
    }

    private fun indices(){
        val collection = listOf('s', 'a', 't')
        //Before
        println(0..collection.size-1)
        //After
        println(list.indices)
        //ผลลัพธ์ 0..2
    }
}
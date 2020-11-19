package com.example.sqliteapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import android.provider.BaseColumns

@Parcelize
data class Friends(var id :String = "",
                  var firstName:String = "",
                  var lastName: String = "",
                  var tel :String = "",
                  var email :String = "",
                  var description :String = "") : Parcelable{

    //Database
    companion object {
        val DATABASE_NAME = "friends.db"
        val DATABASE_VERSION = 1
        val TABLE = "friend"
    }

        object Column {
            val ID = BaseColumns._ID
            val FIRST_NAME = "first_name"
            val LAST_NAME = "last_name"
            val TEL = "tel"
            val EMAIL = "email"
            val DESCRIPTION = "description"
        }
}
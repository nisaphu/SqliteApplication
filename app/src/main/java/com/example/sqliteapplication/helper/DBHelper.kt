package com.example.sqliteapplication.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.sqliteapplication.model.Friends
import android.content.ContentValues
import com.example.sqliteapplication.model.Friends.Companion.TABLE
import com.example.sqliteapplication.model.Friends.Column

class DBHelper(context: Context) : SQLiteOpenHelper(context, "friends.db", null, 1) {

    private val TAG = javaClass.simpleName
    var sqLiteDatabase: SQLiteDatabase? = null

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_FRIEND_TABLE = String.format(
            "CREATE TABLE %s " + "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
            TABLE,
            Column.ID,
            Column.FIRST_NAME,
            Column.LAST_NAME,
            Column.TEL,
            Column.EMAIL,
            Column.DESCRIPTION
        )
        Log.d(TAG, CREATE_FRIEND_TABLE)
        // create friend table
        db.execSQL(CREATE_FRIEND_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + Friends.TABLE
        db.execSQL(DROP_FRIEND_TABLE)
        Log.d(TAG, "Upgrade Database from $oldVersion to $newVersion")
        onCreate(db)
    }

    fun getFriendList(): ArrayList<Friends> {
        val arrayList = ArrayList<Friends>()
        sqLiteDatabase = writableDatabase
        val cursor = sqLiteDatabase!!.query(
            TABLE, null, null, null, null, null, null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val friends = Friends().apply {
                id = cursor.getString(0)
                firstName = cursor.getString(1)
                lastName = cursor.getString(2)
                tel = cursor.getString(3)
                email = cursor.getString(4)
                description = cursor.getString(5)
                cursor.moveToNext()
            }.also {
                arrayList.add(it)
            }
        }
        sqLiteDatabase!!.close()
        return arrayList
    }

    fun getFriendId(friendId: String) : Friends{
        sqLiteDatabase = readableDatabase
        val cursor = sqLiteDatabase!!.query(
            TABLE,
            null,
            Column.ID + " = ? ",
            arrayOf(friendId), null, null, null, null
        )
        cursor?.moveToFirst()

        return Friends().apply {
            id = cursor.getString(0)
            firstName = cursor.getString(1)
            lastName = cursor.getString(2)
            tel = cursor.getString(3)
            email = cursor.getString(4)
            description = cursor.getString(5)
        }
    }

    fun addFriends(friend: Friends) {
        val values = ContentValues()
        sqLiteDatabase = writableDatabase
        //values.put(Friend.Column.ID, friend.getId());
        values.put(Column.FIRST_NAME, friend.firstName)
        values.put(Column.LAST_NAME, friend.lastName)
        values.put(Column.TEL, friend.tel)
        values.put(Column.EMAIL, friend.email)
        values.put(Column.DESCRIPTION, friend.description)
        sqLiteDatabase!!.insert(TABLE, null, values)
        sqLiteDatabase!!.close()
    }

    fun updateFriend(friend: Friends) {
        sqLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put(Column.ID, friend.id)
        values.put(Column.FIRST_NAME, friend.firstName)
        values.put(Column.LAST_NAME, friend.lastName)
        values.put(Column.TEL, friend.tel)
        values.put(Column.EMAIL, friend.email)
        values.put(Column.DESCRIPTION, friend.description)

        val row = sqLiteDatabase!!.update(TABLE,
            values,
            Column.ID + " = ? ",
            arrayOf(friend.id)
        )
        sqLiteDatabase!!.close()
    }

    fun deleteFriend(id: String) {
        sqLiteDatabase = this.writableDatabase
        /*sqLiteDatabase.delete(Friend.TABLE, Friend.Column.ID + " = ? ",
            new String[] { String.valueOf(friend.getId()) });*/
        sqLiteDatabase!!.delete(TABLE, Column.ID + " = " + id, null)
        sqLiteDatabase!!.close()
    }
}

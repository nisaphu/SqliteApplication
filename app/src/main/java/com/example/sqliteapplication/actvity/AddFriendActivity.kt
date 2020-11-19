package com.example.sqliteapplication.actvity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.sqliteapplication.R
import com.example.sqliteapplication.helper.DBHelper
import com.example.sqliteapplication.model.Friends
import kotlinx.android.synthetic.main.activity_add_friend.*

class AddFriendActivity : AppCompatActivity() {

    companion object{
        const val ARG_FRIEND = "arg_friend"
    }

    private var mHelper: DBHelper? = null
    private var id = "-1"
    private var friends = Friends()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        mHelper = DBHelper(this)
        initData()
        initWidget()
    }

    private fun initData(){
        val bundle = intent.extras
        if (bundle != null){
            friends = bundle.getParcelable(ARG_FRIEND)!!

            id = friends.id
            firstNameEt.setText(friends.firstName)
            lastNameEt.setText(friends.lastName)
            teLEt.setText(friends.tel)
            emailEt.setText(friends.email)
            descEt.setText(friends.description)
        }
    }

    private fun initWidget() {
        submitBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Alert")
            builder.setMessage("Do you want to save to database")
            builder.setPositiveButton("ok", DialogInterface.OnClickListener { _, _ ->
                val friends = Friends().apply{
                    firstName = firstNameEt.text.toString()
                    lastName = lastNameEt.text.toString()
                    tel = teLEt.text.toString()
                    email = emailEt.text.toString()
                    description = emailEt.text.toString()
                }
                if (id == "-1") {
                    mHelper!!.addFriends(friends)
                }else{
                    friends.id = id
                    mHelper!!.updateFriend(friends)
                }
                finish()
            })

            builder.setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })

            builder.show()
        }
    }
}

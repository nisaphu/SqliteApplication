package com.example.sqliteapplication.actvity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sqliteapplication.R
import com.example.sqliteapplication.helper.DBHelper
import com.example.sqliteapplication.model.Friends
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val ARG_ID = "arg_id"
        const val ARG_FRIEND = "arg_friend"
    }

    var id = ""
    var mHelper: DBHelper? = null
    var friends = Friends()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_detail)

        mHelper = DBHelper(this)
        initData()
        initWidget()
    }

    private fun initData() {
        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString(ARG_ID, "")
            friends = bundle.getParcelable(ARG_FRIEND)!!
        }
    }

    private fun initWidget() {
        editBtn.setOnClickListener {
            val intent = Intent(this, AddFriendActivity::class.java)
            intent.putExtra(AddFriendActivity.ARG_FRIEND, friends)
            startActivity(intent)
        }
        delBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Alert")
            builder.setMessage("Do you want to delete")
            builder.setPositiveButton("ok", DialogInterface.OnClickListener { _, _ ->
                mHelper!!.deleteFriend(id)
                Toast.makeText(application, "Deleted", Toast.LENGTH_LONG).show()
                finish()
            })

            builder.setNegativeButton("cancel", null)
            builder.show()
        }
    }

    override fun onStart() {
        super.onStart()
        bindData()
    }

    private fun bindData(){
        val item = mHelper!!.getFriendId(id)
        firstNameTv.text = item.firstName
        lastNameTv.text = item.lastName
        telTv.text = item.tel
        emailTv.text = item.email
        descTv.text = item.description
    }
}

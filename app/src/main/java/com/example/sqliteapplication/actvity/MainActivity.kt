package com.example.sqliteapplication.actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqliteapplication.helper.DBHelper
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqliteapplication.R
import com.example.sqliteapplication.actvity.DetailActivity.Companion.ARG_FRIEND
import com.example.sqliteapplication.actvity.DetailActivity.Companion.ARG_ID
import com.example.sqliteapplication.adapter.FriendAdapter
import com.example.sqliteapplication.animation.StarActivity
import com.example.sqliteapplication.canvas.CanvasActivity
import com.example.sqliteapplication.indicator.ViewPagerActivity
import com.example.sqliteapplication.lifecycle.DessertActivity
import com.example.sqliteapplication.video.VideoViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mHelper : DBHelper? = null
    private var friendAdapter: FriendAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mHelper = DBHelper(this)
        friendAdapter = FriendAdapter(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = friendAdapter
        }
        friendAdapter!!.callBack = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ARG_ID, it.id)
            intent.putExtra(ARG_FRIEND, it)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out)
        }
    }

    override fun onStart() {
        super.onStart()
        setUpAdapter()
    }

    private fun setUpAdapter(){
        val friends = mHelper!!.getFriendList()
        if (friendAdapter != null) {
            friendAdapter!!.clearItems()
        }
        friendAdapter!!.addItems(friends)
//        friendAdapter!!.arrayList = mHelper!!.getFriendList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_add) {
            val intent = Intent(this, DessertActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        return super.onOptionsItemSelected(item)
    }
}

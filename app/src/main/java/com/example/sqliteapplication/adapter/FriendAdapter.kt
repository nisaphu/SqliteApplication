package com.example.sqliteapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sqliteapplication.R
import com.example.sqliteapplication.databinding.ItemFriendsBinding
import com.example.sqliteapplication.model.Friends

class FriendAdapter(context: Context) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

     var arrayList : ArrayList<Friends> = ArrayList()
    var callBack : ((Friends)-> Unit)? = null
    private lateinit var binding: ItemFriendsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.item_friends, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = arrayList[position]
        binding.data = item
//        holder.text.text = item.firstName
        holder.itemView.setOnClickListener {
            callBack?.invoke(item)
        }
    }

    override fun getItemCount(): Int = arrayList.size

    fun addItems(friends: ArrayList<Friends>){
        arrayList = friends
        notifyDataSetChanged()
    }

    fun clearItems(){
        arrayList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.text)
    }
}
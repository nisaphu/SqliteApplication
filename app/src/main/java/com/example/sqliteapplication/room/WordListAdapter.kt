package com.example.sqliteapplication.room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqliteapplication.R

class WordListAdapter internal constructor(context: Context) : RecyclerView.Adapter<WordListAdapter.ViewHolder>(){

    private var word = emptyList<Word>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false))
    }

    override fun getItemCount(): Int {
        return word.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = word[position]
        holder.wordItemView.text = item.word
    }

    internal fun setWords(words: List<Word>) {
        word = words
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var wordItemView: TextView = itemView.findViewById(R.id.textView)
    }
}